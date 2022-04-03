package us.hypermediocrity.springclean.adapter.currencyexchange;

import java.util.Currency;

import us.hypermediocrity.springclean.domain.entity.Money;
import us.hypermediocrity.springclean.domain.port.CurrencyExchangePort;

public class CurrencyConverterAdapter implements CurrencyExchangePort {

  @Override
  public Money convert(Money unitPrice, Currency currency) {
    // TODO Auto-generated method stub
    return null;
  }

}
