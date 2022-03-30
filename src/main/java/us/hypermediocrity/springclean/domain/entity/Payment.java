package us.hypermediocrity.springclean.domain.entity;

public class Payment {
  private Money amount;
  private FundTransfer transfer;

  public Payment(Money amount, FundTransfer transfer) {
    this.amount = amount;
    this.transfer = transfer;
  }
}
