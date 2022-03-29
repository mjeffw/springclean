package us.hypermediocrity.springclean.domain.entity;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Currency;
import java.util.List;

public class Invoice {

  private String id;
  private String customerId;
  private LocalDate date;
  private List<LineItem> lineItems;

  public String id() {
    return id;
  }

  public String getCustomerId() {
    return customerId;
  }

  public LocalDate date() {
    return date;
  }

  public List<LineItem> lineItems() {
    return Collections.unmodifiableList(lineItems);
  }

  public Money total() {
    return lineItems.stream().map(LineItem::totalPrice).reduce(Money::plus)
        .orElse(new Money(new BigInteger("0"), Currency.getInstance("USD")));
  }
}
