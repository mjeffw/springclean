package us.hypermediocrity.springclean.domain.port;

import java.util.Currency;

import us.hypermediocrity.springclean.domain.entity.Money;

public interface CurrencyExchangePort {

  Money convert(Money unitPrice, Currency currency);

}
