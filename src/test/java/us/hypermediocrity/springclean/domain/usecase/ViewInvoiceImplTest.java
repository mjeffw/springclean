package us.hypermediocrity.springclean.domain.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
import us.hypermediocrity.springclean.domain.port.CurrencyExchangePort;
import us.hypermediocrity.springclean.domain.port.CustomerPort;
import us.hypermediocrity.springclean.domain.port.InvoicePort;
import us.hypermediocrity.springclean.domain.usecase.transfer.LineItemView;

@ExtendWith(MockitoExtension.class)
class ViewInvoiceImplTest {
  @Mock
  InvoicePort invoicePort;

  @Mock
  CustomerPort customerPort;

  @Spy
  CurrencyExchangePort exchangePort = new CurrencyExchangePort() {
    @Override
    public Money convert(Money amount, Currency currency) {
      return new Money(amount.value * conversion, currency);
    }
  };

  @InjectMocks
  ViewInvoiceUsecase usecase;

  private Invoice simpleInvoice;
  private Invoice multipleItemInvoice;
  private double conversion = 1.0;

  @BeforeEach
  public void setup() {
    simpleInvoice = TestFixtures.invoiceSimple();
    multipleItemInvoice = TestFixtures.invoiceMultiItem();
  }

  @Test
  void viewInvoiceShouldMapFieldsProperty() throws Exception {
    var report = usecase.execute(simpleInvoice);

    assertEquals("Acme Corp", report.customerName());
    assertEquals("account", report.accountNumber());
    assertEquals("simple", report.id());
    assertEquals("2022-03-31", report.date());
    assertEquals("USD 0.00", report.total());
    assertEquals(0, report.items().size());
  }

  @Test
  void viewInvoiceWithLineItemsShouldContainCorrectTotal() throws Exception {
    var report = usecase.execute(multipleItemInvoice);

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
    // Set the customer currency to the Euro ("EUR").
    multipleItemInvoice.customer().currency(Currency.getInstance("EUR"));

    // Set the exchange rate to 0.9 (USD -> EUR)
    conversion = 0.9;

    var report = usecase.execute(multipleItemInvoice);

    assertEquals("Acme Corp", report.customerName());
    assertEquals("account", report.accountNumber());
    assertEquals("multi", report.id());
    assertEquals("2022-01-01", report.date());
    assertEquals("EUR 177.12", report.total());
    assertEquals(3, report.items().size());

    LineItemView lineItemView = report.items().get(0);
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
}
