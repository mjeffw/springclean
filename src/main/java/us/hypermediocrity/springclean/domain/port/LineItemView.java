package us.hypermediocrity.springclean.domain.port;

import us.hypermediocrity.springclean.domain.entity.Money;

public class LineItemView {

  private String productId;
  private int quantity;
  private String unitPrice;
  private String totalPrice;

  public String productId() {
    return productId;
  }

  void productId(String productId) {
    this.productId = productId;
  }

  public int quantity() {
    return quantity;
  }

  void quantity(int quantity) {
    this.quantity = quantity;
  }

  public String unitPrice() {
    return unitPrice;
  }

  void unitPrice(Money unitPrice) {
    this.unitPrice = unitPrice.toString();
  }

  public String totalPrice() {
    return totalPrice;
  }

  void totalPrice(Money totalPrice) {
    this.totalPrice = totalPrice.toString();
  }
}
