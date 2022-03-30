package us.hypermediocrity.springclean.domain.port;

import java.time.format.DateTimeFormatter;

import us.hypermediocrity.springclean.domain.entity.Invoice;
import us.hypermediocrity.springclean.domain.entity.LineItem;

public class InvoiceViewBuilder {
  public static InvoiceViewBuilder newInstance(CurrencyExchangePort exchangePort) {
    return new InvoiceViewBuilder(exchangePort);
  }

  private Invoice invoice;
  private CurrencyExchangePort exchangePort;

  public InvoiceViewBuilder(CurrencyExchangePort exchangePort) {
    this.exchangePort = exchangePort;
  }

  public InvoiceViewBuilder invoice(Invoice invoice) {
    this.invoice = invoice;
    return this;
  }

  public InvoiceView build() {
    var view = new InvoiceView();
    view.customerName(invoice.customerName());
    view.accountNumber(invoice.customerAccount());
    view.invoiceId(invoice.id());
    view.invoiceDate(invoice.date().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

    for (LineItem lineItem : invoice.lineItems()) {
      view.addLineItem(
          LineItemBuilder.newInstance(exchangePort).productId(lineItem.productId()).quantity(lineItem.quantity())
              .unitPrice(lineItem.unitPrice()).totalPrice(lineItem.totalPrice()).currency(invoice.currency()).build());
    }

    var total = exchangePort.convert(invoice.total(), invoice.currency());
    view.invoiceTotal(total);

    return view;
  }

}
