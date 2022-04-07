package us.hypermediocrity.springclean.domain.entity;

import java.util.Currency;

public class Customer {
  @SuppressWarnings("unused")
  private final String id;

  private String name;
  private String accountNumber;
  private Currency currency;

  public Customer(String id) {
    this.id = id;
  }

  public String name() {
    return name;
  }

  public void name(String name) {
    this.name = name;
  }

  public String accountNumber() {
    return accountNumber;
  }

  public void accountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }

  public Currency currency() {
    return currency;
  }

  public void currency(Currency currency) {
    this.currency = currency;
  }
}
