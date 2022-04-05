package us.hypermediocrity.springclean.domain.usecase.transfer;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import us.hypermediocrity.springclean.domain.entity.Invoice;
import us.hypermediocrity.springclean.domain.port.CurrencyExchangePort;

public class InvoiceViewBuilder {
  public static InvoiceViewBuilder newInstance(CurrencyExchangePort exchangePort) {
    return new InvoiceViewBuilder(exchangePort);
  }

  private Invoice invoice;
  private CurrencyExchangePort exchangePort;

  InvoiceViewBuilder(CurrencyExchangePort exchangePort) {
    this.exchangePort = exchangePort;
  }

  public InvoiceViewBuilder invoice(Invoice invoice) {
    this.invoice = invoice;
    return this;
  }

  public InvoiceView build() {
    List<LineItemView> items = invoice.lineItems().stream() //
        .map(it -> LineItemViewBuilder.newInstance(exchangePort) //
            .productId(it.productId())//
            .quantity(it.quantity()) //
            .unitPrice(it.unitPrice()) //
            .totalPrice(it.totalPrice()) //
            .currency(invoice.currency()) //
            .build())
        .collect(Collectors.toList());

    var view = new InvoiceView(invoice.customerName(), invoice.customerAccount(), invoice.id(),
        invoice.date().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), items,
        exchangePort.convert(invoice.total(), invoice.currency()).toString());

    return view;
  }
}
