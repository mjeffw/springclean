package us.hypermediocrity.springclean.domain.port;

import java.time.format.DateTimeFormatter;

import us.hypermediocrity.springclean.domain.entity.Customer;
import us.hypermediocrity.springclean.domain.entity.Invoice;
import us.hypermediocrity.springclean.domain.entity.LineItem;

public class InvoiceViewBuilder {
  public static InvoiceViewBuilder newInstance(CurrencyExchangePort exchangePort) {
    return new InvoiceViewBuilder(exchangePort);
  }

  private Invoice invoice;
  private Customer customer;
  private CurrencyExchangePort exchangePort;

  public InvoiceViewBuilder(CurrencyExchangePort exchangePort) {
    this.exchangePort = exchangePort;
  }

  public InvoiceViewBuilder invoice(Invoice invoice) {
    this.invoice = invoice;
    return this;
  }

  public InvoiceViewBuilder customer(Customer customer) {
    this.customer = customer;
    return this;
  }

  public InvoiceView build() {
    InvoiceView view = new InvoiceView();
    view.customerName(customer.name());
    view.accountNumber(customer.accountNumber());

    view.invoiceId(invoice.id());
    view.invoiceDate(invoice.date().format(DateTimeFormatter.ofPattern("yyyy MM dd")));
    for (LineItem lineItem : invoice.lineItems()) {
      view.addLineItem(
          LineItemBuilder.newInstance(exchangePort).productId(lineItem.productId()).quantity(lineItem.quantity())
              .unitPrice(lineItem.unitPrice()).totalPrice(lineItem.totalPrice()).currency(customer.currency()).build());
    }
    view.invoiceTotal(invoice.total());
    return view;
  }

}
