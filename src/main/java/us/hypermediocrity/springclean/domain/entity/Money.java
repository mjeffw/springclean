package us.hypermediocrity.springclean.domain.entity;

import java.math.BigInteger;
import java.util.Currency;

public class Money {
  private final BigInteger bigInteger;
  private final Currency currency;

  public Money(BigInteger bigInteger, Currency currency) {
    this.bigInteger = bigInteger;
    this.currency = currency;
  }

  public Money times(int quantity) {
    return new Money(bigInteger.multiply(new BigInteger("" + quantity)), currency);
  }

  public Money plus(Money toAdd) {
    return null;
  }

  @Override
  public String toString() {
    return String.format("%s %s", currency.getCurrencyCode(), bigInteger.toString());
  }
}
