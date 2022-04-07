package us.hypermediocrity.springclean.domain.utils;

import static us.hypermediocrity.springclean.common.Money.USD;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import us.hypermediocrity.springclean.common.Money;
import us.hypermediocrity.springclean.domain.common.InvoiceInfo;
import us.hypermediocrity.springclean.domain.common.LineItemInfo;
import us.hypermediocrity.springclean.domain.entity.Invoice;
import us.hypermediocrity.springclean.domain.port.CurrencyExchangeService;

public class InvoiceInfoBuilder {
  public static InvoiceInfoBuilder newInstance(CurrencyExchangeService exchangeService) {
    return new InvoiceInfoBuilder(exchangeService);
  }

  private Invoice invoice;
  private CurrencyExchangeService exchangeService;

  InvoiceInfoBuilder(CurrencyExchangeService exchangeService) {
    this.exchangeService = exchangeService;
  }

  public InvoiceInfoBuilder invoice(Invoice invoice) {
    this.invoice = invoice;
    return this;
  }

  public InvoiceInfo build() {
    List<LineItemInfo> items = invoice.lineItems().stream() //
        .map(it -> LineItemInfoBuilder.newInstance(exchangeService) //
            .productId(it.productId())//
            .quantity(it.quantity()) //
            .unitPrice(it.unitPrice()) //
            .totalPrice(it.totalPrice()) //
            .currency(invoice.currency()) //
            .build())
        .collect(Collectors.toList());

    var rate = exchangeService.getCurrentExchangeRate(USD, invoice.currency());
    var adjustedTotalCost = new Money(invoice.totalCost().value * rate, invoice.currency());

    var view = new InvoiceInfo(invoice.customerName(), invoice.customerAccount(), invoice.id(),
        invoice.date().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), items, adjustedTotalCost.toString());

    return view;
  }
}
