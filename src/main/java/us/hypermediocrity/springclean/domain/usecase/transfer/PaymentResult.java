package us.hypermediocrity.springclean.domain.usecase.transfer;

import us.hypermediocrity.springclean.domain.entity.Money;

public record PaymentResult(Money amountPaid, Reason reason) {
}
