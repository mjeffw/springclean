package us.hypermediocrity.springclean.domain.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Currency;
import java.util.List;

import us.hypermediocrity.springclean.common.Money;

public class Invoice {
  private final String id;
  private String customerId;
  private LocalDate date;
  private List<LineItem> lineItems = new ArrayList<>();
  private Customer customer;
  private Money amountPaid;

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

  public Customer customer() {
    return customer;
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

  public Money totalCost() {
    return lineItems.stream().map(LineItem::totalPrice).reduce(Money::plus).orElse(Money.ZERO);
  }

  public Money amountDue() {
    return totalCost().minus(amountPaid);
  }

  public void amountPaid(Money value) {
    this.amountPaid = value;
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

  public boolean isNothingDue() {
    return Money.ZERO.equals(amountDue());
  }

  public Money pay(Money amount) {
    var amountToPay = (amount.greaterThan(amountDue())) ? amountDue() : amount;
    this.amountPaid = this.amountPaid.plus(amountToPay);

    return amountToPay;
  }
}
