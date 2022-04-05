package us.hypermediocrity.springclean.domain.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static us.hypermediocrity.springclean.domain.entity.Money.USD;

import java.util.Currency;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import us.hypermediocrity.springclean.domain.entity.Invoice;
import us.hypermediocrity.springclean.domain.entity.Money;
import us.hypermediocrity.springclean.domain.entity.Payment;
import us.hypermediocrity.springclean.domain.port.CurrencyExchangePort;
import us.hypermediocrity.springclean.domain.port.InvoicePort;
import us.hypermediocrity.springclean.domain.port.MoneyTransferPort;
import us.hypermediocrity.springclean.domain.port.TransferResponse;
import us.hypermediocrity.springclean.domain.usecase.transfer.Reason;
import us.hypermediocrity.springclean.domain.usecase.transfer.Status;

@ExtendWith(MockitoExtension.class)
class MakePaymentTest {
  @Mock
  InvoicePort invoicePort;

  @Mock
  MoneyTransferPort transferPort;

  @Spy
  CurrencyExchangePort exchangePort = new CurrencyExchangePort() {
    @Override
    public Money convert(Money amount, Currency currency) {
      return new Money(amount.value * exchangeRate, currency);
    }
  };

  @InjectMocks
  private MakePaymentUsecase usecase;

  private Invoice multiInvoice;

  private Invoice simpleInvoice;

  private double amount = 196.80;

  private Money invoicedAmount = new Money(amount, USD);

  private double exchangeRate = 0.9;

  @BeforeEach
  public void setup() {
    simpleInvoice = TestFixtures.invoiceSimple();
    multiInvoice = TestFixtures.invoiceMultiItem();
  }

  @Test
  void exactPaymentOnInvoice() throws Exception {
    var response = new TransferResponse(Status.OK);
    when(transferPort.request(invoicedAmount, null)).thenReturn(response);

    var method = new Payment(invoicedAmount, null);

    var result = usecase.execute(multiInvoice, method);

    assertEquals(invoicedAmount, result.amountPaid());
    assertEquals(Reason.PAID_IN_FULL, result.reason());
  }

  @Test
  void overpaid() throws Exception {
    var payment = invoicedAmount.plus(new Money(0.01, USD));
    var method = new Payment(payment, null);

    var result = usecase.execute(multiInvoice, method);

    assertEquals(Money.ZERO, result.amountPaid());
    assertEquals(Reason.OVERPAID, result.reason());
  }

  @Test
  void paymentFailed() throws Exception {
    var response = new TransferResponse(Status.FAILED);
    when(transferPort.request(invoicedAmount, null)).thenReturn(response);

    var method = new Payment(invoicedAmount, null);

    var result = usecase.execute(multiInvoice, method);

    assertEquals(Money.ZERO, result.amountPaid());
    assertEquals(Reason.TRANSACTION_FAILED, result.reason());
  }

  @Test
  void paymentLessThanInvoiced() throws Exception {
    var payment = invoicedAmount.minus(new Money(0.01, USD));
    var method = new Payment(payment, null);

    var result = usecase.execute(multiInvoice, method);

    assertEquals(Money.ZERO, result.amountPaid());
    assertEquals(Reason.NOT_PAID_IN_FULL, result.reason());
  }

  @Test
  void zeroDue() throws Exception {
    var method = new Payment(new Money(400, USD), null);

    var result = usecase.execute(simpleInvoice, method);

    assertEquals(Money.ZERO, result.amountPaid());
    assertEquals(Reason.AMOUNT_DUE_IS_ZERO, result.reason());
  }

  @Test
  void paymentInOtherCurrency() throws Exception {
    var response = new TransferResponse(Status.OK);
    when(transferPort.request(invoicedAmount, null)).thenReturn(response);

    Money otherCurrencyPayment = new Money(amount / exchangeRate, Currency.getInstance("EUR"));
    var method = new Payment(otherCurrencyPayment, null);

    var result = usecase.execute(multiInvoice, method);

    assertEquals(otherCurrencyPayment, result.amountPaid());
    assertEquals(Reason.PAID_IN_FULL, result.reason());
  }
}
