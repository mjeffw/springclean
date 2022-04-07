package us.hypermediocrity.springclean.domain.port;

import java.util.Currency;

import us.hypermediocrity.springclean.common.Money;

public interface CurrencyExchangeService {
  Money convert(Money amount, Currency usd);

  double getCurrentExchangeRate(Currency currency, Currency usd);
}
