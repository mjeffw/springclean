package us.hypermediocrity.springclean.domain.usecase.transfer;

public record LineItemView(String productId, int quantity, String unitPrice, String totalPrice) {
}
