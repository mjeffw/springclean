package us.hypermediocrity.springclean.domain.common;

public record LineItemInfo(String productId, int quantity, String unitPrice, String totalPrice) {
}
