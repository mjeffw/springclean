package us.hypermediocrity.springclean.domain.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Currency;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import us.hypermediocrity.springclean.domain.entity.Invoice;
import us.hypermediocrity.springclean.domain.entity.Money;
import us.hypermediocrity.springclean.domain.entity.Payment;
import us.hypermediocrity.springclean.domain.entity.PaymentResult.Reason;
import us.hypermediocrity.springclean.domain.port.InvoicePort;

@ExtendWith(MockitoExtension.class)
class MakePaymentTest {
  @Mock
  InvoicePort invoicePort;

  @InjectMocks
  private MakePayment usecase;

  private Invoice simpleInvoice;
  private Invoice multiInvoice;
  private static Currency USD = Currency.getInstance("USD");

  @BeforeEach
  public void setup() {
    this.simpleInvoice = TestFixtures.invoiceSimple();
    this.multiInvoice = TestFixtures.invoiceMultiItem();
  }

  @Test
  void paymentOnInvoiceWithZeroDue() throws Exception {
    var method = new Payment(new Money(400, USD), null);

    var result = usecase.execute(simpleInvoice, method);

    assertEquals(Money.ZERO, result.amountPaid());
    assertEquals(Reason.AMOUNT_DUE_IS_ZERO, result.reason());
  }

  @Test
  void exactPaymentOnInvoice() throws Exception {
    var method = new Payment(new Money(196.80, USD), null);

    var result = usecase.execute(multiInvoice, method);

    assertEquals(new Money(196.80, USD), result.amountPaid());
    assertEquals(Reason.PAID_IN_FULL, result.reason());
  }
}
