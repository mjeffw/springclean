package us.hypermediocrity.springclean.domain.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static us.hypermediocrity.springclean.common.Money.USD;
import static us.hypermediocrity.springclean.domain.usecase.TestFixtures.customer;

import java.util.Currency;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import us.hypermediocrity.springclean.domain.common.CustomerNotFoundException;
import us.hypermediocrity.springclean.domain.common.InvoiceNotFoundException;
import us.hypermediocrity.springclean.domain.common.LineItemInfo;
import us.hypermediocrity.springclean.domain.entity.Invoice;
import us.hypermediocrity.springclean.domain.port.CurrencyExchangeService;
import us.hypermediocrity.springclean.domain.port.CustomerEntityGateway;
import us.hypermediocrity.springclean.domain.port.InvoiceEntityGateway;

@ExtendWith(MockitoExtension.class)
class ViewInvoiceTest {
  @Mock InvoiceEntityGateway invoices;
  @Mock CustomerEntityGateway customers;
  @Mock CurrencyExchangeService exchangeService;
  @InjectMocks ViewInvoiceUsecase usecase;

  private Invoice simpleInvoice;
  private Invoice multiInvoice;
  private double exchangeRate = 1.0;

  @BeforeEach
  public void setup() {
    simpleInvoice = TestFixtures.invoiceSimple();
    multiInvoice = TestFixtures.invoiceMultiItem();
  }

  @Test
  void viewInvoiceShouldMapFieldsProperty() throws Exception {
    when(invoices.getInvoice("simple")).thenReturn(Optional.of(simpleInvoice));
    when(exchangeService.getCurrentExchangeRate(USD, USD)).thenReturn(exchangeRate);
    when(customers.getCustomer("customer")).thenReturn(Optional.of(customer()));

    var report = usecase.execute("simple");

    assertEquals("Acme Corp", report.customerName());
    assertEquals("account", report.accountNumber());
    assertEquals("simple", report.id());
    assertEquals("2022-03-31", report.date());
    assertEquals("USD 0.00", report.total());
    assertEquals(0, report.items().size());
  }

  @Test
  void viewInvoiceWithLineItemsShouldContainCorrectTotal() throws Exception {
    when(invoices.getInvoice("multi")).thenReturn(Optional.of(multiInvoice));
    when(exchangeService.getCurrentExchangeRate(USD, USD)).thenReturn(exchangeRate);
    when(customers.getCustomer("customer")).thenReturn(Optional.of(customer()));

    var report = usecase.execute("multi");

    assertEquals("Acme Corp", report.customerName());
    assertEquals("account", report.accountNumber());
    assertEquals("multi", report.id());
    assertEquals("2022-01-01", report.date());
    assertEquals("USD 196.80", report.total());
    assertEquals(3, report.items().size());

    var lineItemView = report.items().get(0);
    assertEquals("product-a", lineItemView.productId());
    assertEquals(4, lineItemView.quantity());
    assertEquals("USD 39.95", lineItemView.unitPrice());
    assertEquals("USD 159.80", lineItemView.totalPrice());

    lineItemView = report.items().get(1);
    assertEquals("product-b", lineItemView.productId());
    assertEquals(1, lineItemView.quantity());
    assertEquals("USD 2.00", lineItemView.unitPrice());
    assertEquals("USD 2.00", lineItemView.totalPrice());

    lineItemView = report.items().get(2);
    assertEquals("product-c", lineItemView.productId());
    assertEquals(2, lineItemView.quantity());
    assertEquals("USD 17.50", lineItemView.unitPrice());
    assertEquals("USD 35.00", lineItemView.totalPrice());
  }

  @Test
  void viewInvoiceWithSomeOtherCurrency() throws Exception {
    when(invoices.getInvoice("multi")).thenReturn(Optional.of(multiInvoice));
    when(customers.getCustomer("customer")).thenReturn(Optional.of(customer()));

    // Set the exchange rate to 0.9 (USD -> EUR).
    exchangeRate = 0.9;

    // Set the customer currency to the Euro ("EUR").
    var customer = customer();
    Currency EUR = Currency.getInstance("EUR");
    customer.currency(EUR);
    when(customers.getCustomer("customer")).thenReturn(Optional.of(customer));
    when(exchangeService.getCurrentExchangeRate(USD, EUR)).thenReturn(exchangeRate);

    multiInvoice.customer().currency(EUR);

    var report = usecase.execute("multi");

    assertEquals("Acme Corp", report.customerName());
    assertEquals("account", report.accountNumber());
    assertEquals("multi", report.id());
    assertEquals("2022-01-01", report.date());
    assertEquals("EUR 177.12", report.total());
    assertEquals(3, report.items().size());

    LineItemInfo lineItemView = report.items().get(0);
    assertEquals("product-a", lineItemView.productId());
    assertEquals(4, lineItemView.quantity());
    assertEquals("EUR 35.96", lineItemView.unitPrice());
    assertEquals("EUR 143.82", lineItemView.totalPrice());

    lineItemView = report.items().get(1);
    assertEquals("product-b", lineItemView.productId());
    assertEquals(1, lineItemView.quantity());
    assertEquals("EUR 1.80", lineItemView.unitPrice());
    assertEquals("EUR 1.80", lineItemView.totalPrice());

    lineItemView = report.items().get(2);
    assertEquals("product-c", lineItemView.productId());
    assertEquals(2, lineItemView.quantity());
    assertEquals("EUR 15.75", lineItemView.unitPrice());
    assertEquals("EUR 31.50", lineItemView.totalPrice());
  }

  @Test
  void badInvoiceNumber() throws Exception {
    assertThrows(InvoiceNotFoundException.class, () -> usecase.execute("badinvoice"));
  }

  @Test
  void badCustomerNumber() throws Exception {
    when(invoices.getInvoice("simple")).thenReturn(Optional.of(simpleInvoice));
    when(customers.getCustomer("customer")).thenReturn(Optional.empty());

    assertThrows(CustomerNotFoundException.class, () -> usecase.execute("simple"));
  }
}
