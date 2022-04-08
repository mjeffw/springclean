package us.hypermediocrity.springclean.domain.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static us.hypermediocrity.springclean.common.Money.USD;
import static us.hypermediocrity.springclean.domain.port.PaymentType.CreditCard;

import java.util.Currency;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import us.hypermediocrity.springclean.common.Money;
import us.hypermediocrity.springclean.domain.entity.Invoice;
import us.hypermediocrity.springclean.domain.port.CurrencyExchangeService;
import us.hypermediocrity.springclean.domain.port.Invoices;
import us.hypermediocrity.springclean.domain.port.PaymentInfo;
import us.hypermediocrity.springclean.domain.port.PaymentResponse;
import us.hypermediocrity.springclean.domain.port.PaymentService;
import us.hypermediocrity.springclean.domain.port.PaymentStatus;
import us.hypermediocrity.springclean.domain.usecase.common.InvoiceNotFoundException;
import us.hypermediocrity.springclean.domain.usecase.common.PaymentReason;

@ExtendWith(MockitoExtension.class)
class MakePaymentTest {
  @Mock Invoices invoices;
  @Mock PaymentService paymentService;
  @Mock CurrencyExchangeService exchangePort;
  @InjectMocks private MakePaymentUsecase usecase;

  private Invoice multiInvoice;
  private Invoice simpleInvoice;
  private double amount = 196.80;
  private Money invoicedAmount = new Money(amount, USD);
  private double exchangeRateEuroToDollar = 0.9;
  private double exchangeRateDollarToEuro = 1.0 / exchangeRateEuroToDollar;

  @BeforeEach
  public void setup() {
    simpleInvoice = TestFixtures.invoiceSimple();
    multiInvoice = TestFixtures.invoiceMultiItem();
  }

  @Test
  void exactPaymentOnInvoice() throws Exception {
    when(invoices.getInvoice("multi")).thenReturn(Optional.of(multiInvoice));
    var response = new PaymentResponse(PaymentStatus.SUCCESS);
    PaymentInfo paymentInfo = new PaymentInfo(CreditCard);
    when(paymentService.request(invoicedAmount, paymentInfo)).thenReturn(response);

    var result = usecase.execute("multi", invoicedAmount, paymentInfo);

    assertEquals(invoicedAmount, result.amountPaid());
    assertEquals(PaymentReason.PAID_IN_FULL, result.reason());

    ArgumentCaptor<Money> amount = ArgumentCaptor.forClass(Money.class);
    ArgumentCaptor<PaymentInfo> payment = ArgumentCaptor.forClass(PaymentInfo.class);
    verify(paymentService).request(amount.capture(), payment.capture());
    assertEquals(invoicedAmount, amount.getValue());
    assertEquals(CreditCard, payment.getValue().type());
  }

  @Test
  void overpaid() throws Exception {
    when(invoices.getInvoice("multi")).thenReturn(Optional.of(multiInvoice));
    var payment = invoicedAmount.plus(new Money(0.01, USD));
    PaymentInfo paymentInfo = new PaymentInfo(CreditCard);
    when(paymentService.request(payment, paymentInfo)).thenReturn(new PaymentResponse(PaymentStatus.SUCCESS));

    var result = usecase.execute("multi", payment, paymentInfo);

    assertEquals(invoicedAmount, result.amountPaid());
    assertEquals(PaymentReason.PAID_IN_FULL, result.reason());
    assertEquals(new Money(-0.01, USD), result.balance());
  }

  @Test
  void paymentFailed() throws Exception {
    when(invoices.getInvoice("multi")).thenReturn(Optional.of(multiInvoice));
    var response = new PaymentResponse(PaymentStatus.FAILED);
    when(paymentService.request(eq(invoicedAmount), any(PaymentInfo.class))).thenReturn(response);

    var result = usecase.execute("multi", invoicedAmount, new PaymentInfo(CreditCard));

    assertEquals(Money.ZERO, result.amountPaid());
    assertEquals(PaymentReason.PROCESSING_FAILED, result.reason());
  }

  @Test
  void paymentLessThanInvoiced() throws Exception {
    when(invoices.getInvoice("multi")).thenReturn(Optional.of(multiInvoice));

    var paymentInfo = new PaymentInfo(CreditCard);
    var payment = invoicedAmount.minus(new Money(0.01, USD));
    when(paymentService.request(payment, paymentInfo)).thenReturn(new PaymentResponse(PaymentStatus.SUCCESS));

    var result = usecase.execute("multi", payment, paymentInfo);

    assertEquals(payment, result.amountPaid());
    assertEquals(PaymentReason.PARTIAL_PAYMENT, result.reason());
    assertEquals(new Money(0.01, USD), result.balance());
  }

  @Test
  void zeroDue() throws Exception {
    when(invoices.getInvoice("simple")).thenReturn(Optional.of(simpleInvoice));
    var result = usecase.execute("simple", invoicedAmount, new PaymentInfo(CreditCard));

    assertEquals(Money.ZERO, result.amountPaid());
    assertEquals(PaymentReason.AMOUNT_DUE_IS_ZERO, result.reason());
  }

  @Test
  void paymentInOtherCurrency() throws Exception {
    when(invoices.getInvoice("multi")).thenReturn(Optional.of(multiInvoice));
    PaymentInfo paymentInfo = new PaymentInfo(CreditCard);
    when(paymentService.request(invoicedAmount, paymentInfo)).thenReturn(new PaymentResponse(PaymentStatus.SUCCESS));
    when(exchangePort.getCurrentExchangeRate(Currency.getInstance("EUR"), USD)).thenReturn(exchangeRateEuroToDollar);

    Money paymentInEuros = new Money(amount * exchangeRateDollarToEuro, Currency.getInstance("EUR"));
    var result = usecase.execute("multi", paymentInEuros, paymentInfo);

    assertEquals(paymentInEuros, result.amountPaid());
    assertEquals(PaymentReason.PAID_IN_FULL, result.reason());
  }

  @Test
  void badInvoiceNumber() throws Exception {
    when(invoices.getInvoice("simple")).thenReturn(Optional.empty());
    assertThrows(InvoiceNotFoundException.class, () -> usecase.execute("simple", null, null));
  }
}
