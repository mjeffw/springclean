package us.hypermediocrity.springclean.adapter.currencyexchange;

import java.util.Currency;

import us.hypermediocrity.springclean.common.Money;
import us.hypermediocrity.springclean.domain.port.CurrencyExchangeService;

public class CurrencyConverterAdapter implements CurrencyExchangeService {

  @Override
  public Money convert(Money amount, Currency usd) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public double getCurrentExchangeRate(Currency currency, Currency usd) {
    // TODO Auto-generated method stub
    return 0;
  }
}
