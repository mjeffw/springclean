package us.hypermediocrity.springclean.domain.usecase.transfer;

import java.util.Currency;

import us.hypermediocrity.springclean.domain.entity.Money;
import us.hypermediocrity.springclean.domain.port.CurrencyExchangePort;

class LineItemViewBuilder {
  static LineItemViewBuilder newInstance(CurrencyExchangePort exchangePort) {
    return new LineItemViewBuilder(exchangePort);
  }

  private String productId;
  private int quantity;
  private Money unitPrice;
  private Money totalPrice;
  private Currency currency;
  private CurrencyExchangePort exchangePort;

  LineItemViewBuilder(CurrencyExchangePort exchangePort) {
    this.exchangePort = exchangePort;
  }

  LineItemViewBuilder productId(String productId) {
    this.productId = productId;
    return this;
  }

  LineItemViewBuilder quantity(int quantity) {
    this.quantity = quantity;
    return this;
  }

  LineItemViewBuilder unitPrice(Money unitPrice) {
    this.unitPrice = unitPrice;
    return this;
  }

  LineItemViewBuilder totalPrice(Money totalPrice) {
    this.totalPrice = totalPrice;
    return this;
  }

  LineItemViewBuilder currency(Currency currency) {
    this.currency = currency;
    return this;
  }

  LineItemView build() {
    var item = new LineItemView(productId, quantity, exchangePort.convert(unitPrice, currency).toString(),
        exchangePort.convert(totalPrice, currency).toString());
    return item;
  }
}
