package us.hypermediocrity.springclean.domain.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.Currency;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import us.hypermediocrity.springclean.domain.entity.Customer;
import us.hypermediocrity.springclean.domain.entity.Invoice;
import us.hypermediocrity.springclean.domain.entity.Money;
import us.hypermediocrity.springclean.domain.entity.Payment;
import us.hypermediocrity.springclean.domain.port.InvoicePort;

class MakePaymentTest {
  @Mock
  InvoicePort invoicePort;

  @InjectMocks
  private MakePaymentUsecase usecase;

  private Invoice simpleInvoice;
  private Customer customer;

  @BeforeEach
  public void setup() {
    simpleInvoice = new Invoice("simple");
    simpleInvoice.date(LocalDate.of(2022, 3, 31));
    simpleInvoice.customerId("customer");

    customer = new Customer("customer");
    customer.accountNumber("account");
    customer.name("Acme Corp");
    customer.currency(Currency.getInstance("USD"));
  }

  @Test
  void paymentOnInvoiceWithZeroDue() throws Exception {

    var amount = new Money(400, Currency.getInstance("USD"));
    var method = new Payment(amount, null); // Transfer should not matter, no payment will be executed.

    var result = usecase.execute(simpleInvoice, method);

    assertEquals(new Money(0, Currency.getInstance("USD")), result.amountPaid());
  }
}
