package us.hypermediocrity.springclean.domain.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Currency;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import us.hypermediocrity.springclean.domain.entity.Customer;
import us.hypermediocrity.springclean.domain.entity.Invoice;
import us.hypermediocrity.springclean.domain.port.CurrencyExchangePort;
import us.hypermediocrity.springclean.domain.port.CustomerPort;
import us.hypermediocrity.springclean.domain.port.InvoicePort;
import us.hypermediocrity.springclean.domain.usecase.exceptions.InvoiceNotFoundException;

@ExtendWith(MockitoExtension.class)
class ViewInvoiceImplTest {
  @Mock
  InvoicePort invoicePort;

  @Mock
  CustomerPort customerPort;

  @Mock
  CurrencyExchangePort exchangePort;

  @InjectMocks
  ViewInvoiceUsecase usecase;

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
  void viewUnknownInvoiceNumberShouldThrowException() throws Exception {
    when(invoicePort.getInvoice("1234")).thenReturn(null);

    assertThrows(InvoiceNotFoundException.class, () -> usecase.execute("1234"));
  }

  @Test
  void viewInvoiceShouldMapFieldsProperty() throws Exception {
    when(invoicePort.getInvoice("simple")).thenReturn(simpleInvoice);
    when(customerPort.getCustomer("customer")).thenReturn(customer);

    var report = usecase.execute("simple");

    assertEquals("Acme Corp", report.customerName());
    assertEquals("account", report.accountNumber());
    assertEquals("simple", report.invoiceId());
    assertEquals("2022-03-31", report.invoiceDate());
    assertEquals("USD 0", report.invoiceTotal());
    assertEquals(0, report.lineItems().size());
  }
}
