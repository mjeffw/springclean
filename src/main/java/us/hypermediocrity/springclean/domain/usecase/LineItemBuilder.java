package us.hypermediocrity.springclean.domain.usecase;

import java.util.Currency;

import us.hypermediocrity.springclean.domain.entity.Money;
import us.hypermediocrity.springclean.domain.port.CurrencyExchangePort;

class LineItemBuilder {
  static LineItemBuilder newInstance(CurrencyExchangePort exchangePort) {
    return new LineItemBuilder(exchangePort);
  }

  private String productId;
  private int quantity;
  private Money unitPrice;
  private Money totalPrice;
  private Currency currency;
  private CurrencyExchangePort exchangePort;

  LineItemBuilder(CurrencyExchangePort exchangePort) {
    this.exchangePort = exchangePort;
  }

  LineItemBuilder productId(String productId) {
    this.productId = productId;
    return this;
  }

  LineItemBuilder quantity(int quantity) {
    this.quantity = quantity;
    return this;
  }

  LineItemBuilder unitPrice(Money unitPrice) {
    this.unitPrice = unitPrice;
    return this;
  }

  LineItemBuilder totalPrice(Money totalPrice) {
    this.totalPrice = totalPrice;
    return this;
  }

  LineItemBuilder currency(Currency currency) {
    this.currency = currency;
    return this;
  }

  LineItemView build() {
    var item = new LineItemView();
    item.productId(productId);
    item.quantity(quantity);
    item.unitPrice(exchangePort.convert(unitPrice, currency));
    item.totalPrice(exchangePort.convert(totalPrice, currency));
    return item;
  }
}
