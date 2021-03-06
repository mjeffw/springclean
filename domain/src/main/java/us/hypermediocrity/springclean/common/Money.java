package us.hypermediocrity.springclean.common;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Currency;

public class Money {
  public static final Currency USD = Currency.getInstance("USD");
  public static final Money ZERO = new Money(0.0, Money.USD);
  public final Currency currency;
  public final double value;

  public Money(double value, Currency currency) {
    this.value = value;
    this.currency = currency;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Money)) {
      return false;
    }
    var money = (Money) obj;
    var thisValue = new BigDecimal(this.value, MathContext.DECIMAL32);
    var otherValue = new BigDecimal(money.value, MathContext.DECIMAL32);
    return (thisValue.equals(otherValue) && this.currency == money.currency);
  }

  public boolean greaterThan(Money other) {
    if (other.value == 0 || this.value == 0 || other.currency == this.currency) {
      return this.value > other.value;
    }
    throw new IllegalArgumentException("Must be the same currency.");
  }

  public boolean lessThan(Money other) {
    if (other.value == 0 || this.value == 0 || other.currency == this.currency) {
      return this.value < other.value;
    }
    throw new IllegalArgumentException("Must be the same currency.");
  }

  public Money plus(Money toAdd) {
    if (this.currency == toAdd.currency) {
      return new Money(value + toAdd.value, currency);
    }
    throw new IllegalArgumentException();
  }

  public Money minus(Money other) {
    return this.plus(new Money(other.value * -1, other.currency));
  }

  public Money times(double d) {
    return new Money(value * d, currency);
  }

  @Override
  public String toString() {
    return String.format("%s %.2f", currency.getCurrencyCode(), value);
  }
}
