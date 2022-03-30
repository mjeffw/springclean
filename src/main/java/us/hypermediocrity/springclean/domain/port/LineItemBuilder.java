package us.hypermediocrity.springclean.domain.port;

import java.util.Currency;

import us.hypermediocrity.springclean.domain.entity.Money;

class LineItemBuilder {
  public static LineItemBuilder newInstance(CurrencyExchangePort exchangePort) {
    return new LineItemBuilder(exchangePort);
  }

  private String productId;
  private int quantity;
  private Money unitPrice;
  private Money totalPrice;
  private Currency currency;
  private CurrencyExchangePort exchangePort;

  public LineItemBuilder(CurrencyExchangePort exchangePort) {
    this.exchangePort = exchangePort;
  }

  public LineItemBuilder productId(String productId) {
    this.productId = productId;
    return this;
  }

  public LineItemBuilder quantity(int quantity) {
    this.quantity = quantity;
    return this;
  }

  public LineItemBuilder unitPrice(Money unitPrice) {
    this.unitPrice = unitPrice;
    return this;
  }

  public LineItemBuilder totalPrice(Money totalPrice) {
    this.totalPrice = totalPrice;
    return this;
  }

  public LineItemBuilder currency(Currency currency) {
    this.currency = currency;
    return this;
  }

  public LineItemView build() {
    var item = new LineItemView();
    item.productId(productId);
    item.quantity(quantity);
    item.unitPrice(exchangePort.convert(unitPrice, currency));
    item.totalPrice(exchangePort.convert(totalPrice, currency));
    return item;
  }
}
