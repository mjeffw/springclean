package us.hypermediocrity.springclean.domain.entity;

public class LineItem {
  private String productId;
  private int quantity;
  private Money unitPrice;

  public String productId() {
    return productId;
  }

  public int quantity() {
    return quantity;
  }

  public Money unitPrice() {
    return unitPrice;
  }

  public Money totalPrice() {
    return unitPrice.times(quantity);
  }
}
