package us.hypermediocrity.springclean.domain.entity;

import java.util.Currency;

public class Money {
  public final double value;
  public final Currency currency;

  public Money(double value, Currency currency) {
    this.value = value;
    this.currency = currency;
  }

  public Money times(int quantity) {
    return new Money(value * quantity, currency);
  }

  public Money plus(Money toAdd) {
    if (this.currency == toAdd.currency) {
      return new Money(value + toAdd.value, currency);
    }
    throw new IllegalArgumentException();
  }

  @Override
  public String toString() {
    return String.format("%s %.2f", currency.getCurrencyCode(), value);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!(obj instanceof Money))
      return false;
    var money = (Money) obj;
    return (this.value == money.value && this.currency == money.currency);
  }
}
