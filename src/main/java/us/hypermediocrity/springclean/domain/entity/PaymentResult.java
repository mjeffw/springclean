package us.hypermediocrity.springclean.domain.entity;

public class PaymentResult {

  public static enum Reason {
    AMOUNT_DUE_IS_ZERO, PAID_IN_FULL
  }

  private Money amountPaid;
  private Reason reason;

  public PaymentResult(Money amountPaid, Reason reason) {
    this.amountPaid = amountPaid;
    this.reason = reason;
  }

  public Money amountPaid() {
    return amountPaid;
  }

  public Reason reason() {
    return reason;
  }
}
