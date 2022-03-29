package us.hypermediocrity.springclean.domain.entity;

import java.util.Currency;

public class Customer {
  private String name;
  private String accountNumber;
  private Currency currency;

  public String name() {
    return name;
  }

  public String accountNumber() {
    return accountNumber;
  }

  public Currency currency() {
    return currency;
  }
}
