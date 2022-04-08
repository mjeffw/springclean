package us.hypermediocrity.springclean.domain.utils;

import java.util.Currency;

import us.hypermediocrity.springclean.common.Money;
import us.hypermediocrity.springclean.domain.port.CurrencyExchangeService;
import us.hypermediocrity.springclean.domain.usecase.common.LineItemInfo;

class LineItemInfoBuilder {
  static LineItemInfoBuilder newInstance(CurrencyExchangeService exchangeService) {
    return new LineItemInfoBuilder(exchangeService);
  }

  private String productId;
  private int quantity;
  private Money unitPrice;
  private Money totalPrice;
  private Currency currency;
  private CurrencyExchangeService exchangeService;

  LineItemInfoBuilder(CurrencyExchangeService exchangeService) {
    this.exchangeService = exchangeService;
  }

  LineItemInfoBuilder productId(String productId) {
    this.productId = productId;
    return this;
  }

  LineItemInfoBuilder quantity(int quantity) {
    this.quantity = quantity;
    return this;
  }

  LineItemInfoBuilder unitPrice(Money unitPrice) {
    this.unitPrice = unitPrice;
    return this;
  }

  LineItemInfoBuilder totalPrice(Money totalPrice) {
    this.totalPrice = totalPrice;
    return this;
  }

  LineItemInfoBuilder currency(Currency currency) {
    this.currency = currency;
    return this;
  }

  LineItemInfo build() {
    var exchangeRate = exchangeService.getCurrentExchangeRate(unitPrice.currency, currency);
    var convertedUnitPrice = new Money(unitPrice.value * exchangeRate, currency);
    var convertedTotalPrice = new Money(totalPrice.value * exchangeRate, currency);
    var item = new LineItemInfo(productId, quantity, convertedUnitPrice.toString(), convertedTotalPrice.toString());
    return item;
  }
}
