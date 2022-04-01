package us.hypermediocrity.springclean.domain.entity;

import java.util.Properties;

public class FundTransfer {
  public enum Type {
    CreditCard, PayPal, BankTransfer
  }

  private Type type;

  /**
   * Properties holds the data specific to a given type of FundsTransfer. It's
   * just a "blob" of data that flows through the domain.
   * 
   * Specific data depends on the type: CreditCard requires Name, CC number,
   * Security Code, Expiry Date; BankTransfer requires Name, Account Number,
   * Routing Number; etc...
   */
  private Properties properties;

  public FundTransfer(Type type, Properties properties) {
    this.type = type;
    this.properties = properties;
  }
}
