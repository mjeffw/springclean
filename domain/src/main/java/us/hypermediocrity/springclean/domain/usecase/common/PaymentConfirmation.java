package us.hypermediocrity.springclean.domain.usecase.common;

import us.hypermediocrity.springclean.common.Money;

public record PaymentConfirmation(Money amountPaid, PaymentReason reason, Money balance) {
}
