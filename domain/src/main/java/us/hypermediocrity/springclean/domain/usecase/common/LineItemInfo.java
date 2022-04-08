package us.hypermediocrity.springclean.domain.usecase.common;

public record LineItemInfo(String productId, int quantity, String unitPrice, String totalPrice) {
}
