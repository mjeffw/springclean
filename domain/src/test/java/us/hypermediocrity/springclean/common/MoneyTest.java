package us.hypermediocrity.springclean.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static us.hypermediocrity.springclean.common.Money.USD;

import java.util.Currency;

import org.junit.jupiter.api.Test;

class MoneyTest {

  private Currency EUR = Currency.getInstance("EUR");

  @Test
  void testConstructor() {
    Money one = new Money(0.0, USD);
    assertEquals(0.0, one.value);
    assertEquals(USD, one.currency);
  }

  @Test
  void testEquals() throws Exception {
    Money one = new Money(1.0, USD);
    assertNotEquals("1.00 USD", one);
    assertNotEquals(one, "1.00 USD");
    assertNotEquals(one, new Money(1.01, USD));
    assertNotEquals(one, new Money(1.00, EUR));
    assertEquals("EUR 1.00", new Money(1.00, EUR).toString());

    assertEquals(one, one);
    assertEquals(one, new Money(1.00, USD));
  }

  @Test
  void testGreaterThan() throws Exception {
    Money one = new Money(2.34, USD);
    assertThrows(IllegalArgumentException.class, () -> one.greaterThan(new Money(2.34, EUR)));
    assertTrue(one.greaterThan(new Money(0.0, EUR)));
    assertFalse(new Money(0.0, EUR).greaterThan(one));
    assertFalse(new Money(1.23, USD).greaterThan(one));
    assertFalse(one.greaterThan(one));
    assertTrue(one.greaterThan(new Money(2.33, USD)));
  }

  @Test
  void testLessThan() throws Exception {
    Money one = new Money(2.34, USD);
    assertThrows(IllegalArgumentException.class, () -> one.lessThan(new Money(2.34, EUR)));
    assertFalse(one.lessThan(new Money(0.0, EUR)));
    assertTrue(new Money(0.0, EUR).lessThan(one));
    assertTrue(new Money(1.23, USD).lessThan(one));
    assertFalse(one.lessThan(one));
    assertFalse(one.lessThan(new Money(2.33, USD)));
  }

  @Test
  void testPlus() throws Exception {
    Money one = new Money(2.34, USD);
    assertThrows(IllegalArgumentException.class, () -> one.plus(new Money(1.00, EUR)));
    assertEquals(new Money(2.35, USD), one.plus(new Money(0.01, USD)));
    assertEquals(new Money(2.35, USD), new Money(0.01, USD).plus(one));
    assertEquals(new Money(2.33, USD), new Money(-0.01, USD).plus(one));
  }

  @Test
  void testMinus() throws Exception {
    Money one = new Money(2.34, USD);
    assertThrows(IllegalArgumentException.class, () -> one.minus(new Money(1.00, EUR)));
    assertEquals(new Money(2.33, USD), one.minus(new Money(0.01, USD)));
    assertEquals(new Money(2.35, USD), one.minus(new Money(-0.01, USD)));
    assertEquals(new Money(-2.34, USD), Money.ZERO.minus(one));
    assertEquals("USD -2.34", Money.ZERO.minus(one).toString());
  }

  @Test
  void testTimes() throws Exception {
    Money one = new Money(2.34, USD);
    assertEquals(new Money(4.68, USD), one.times(2.0));
    assertEquals("USD 4.68", one.times(2.0).toString());
  }
}
