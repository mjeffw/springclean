package us.hypermediocrity.springclean.domain.usecase.transfer;

public enum Reason {
  AMOUNT_DUE_IS_ZERO, PAID_IN_FULL, NOT_PAID_IN_FULL, OVERPAID, TRANSACTION_FAILED
}