package us.hypermediocrity.springclean.domain.entity;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Currency;
import java.util.List;

public class Invoice {
  private final String id;
  private String customerId;
  private LocalDate date;
  private List<LineItem> lineItems = new ArrayList<>();

  public Invoice(String id) {
    this.id = id;
  }

  public String id() {
    return id;
  }

  public String customerId() {
    return customerId;
  }

  public void customerId(String customerId) {
    this.customerId = customerId;
  }

  public LocalDate date() {
    return date;
  }

  public void date(LocalDate date) {
    this.date = date;
  }

  public List<LineItem> lineItems() {
    return Collections.unmodifiableList(lineItems);
  }

  public Money total() {
    return lineItems.stream().map(LineItem::totalPrice).reduce(Money::plus)
        .orElse(new Money(new BigInteger("0"), Currency.getInstance("USD")));
  }
}
