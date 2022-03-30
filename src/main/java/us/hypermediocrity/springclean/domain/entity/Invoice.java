package us.hypermediocrity.springclean.domain.entity;

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
  private Customer customer;

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

  public void customer(Customer customer) {
    this.customer = customer;
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

  public void addLineItem(LineItem item) {
    this.lineItems.add(item);
  }

  public Money total() {
    return lineItems.stream().map(LineItem::totalPrice).reduce(Money::plus)
        .orElse(new Money(0.0, Currency.getInstance("USD")));
  }

  public Currency currency() {
    return customer.currency();
  }

  public String customerName() {
    return customer.name();
  }

  public String customerAccount() {
    return customer.accountNumber();
  }
}
