package us.hypermediocrity.springclean.domain.usecase;

import us.hypermediocrity.springclean.domain.entity.Invoice;
import us.hypermediocrity.springclean.domain.entity.Payment;
import us.hypermediocrity.springclean.domain.entity.PaymentResult;
import us.hypermediocrity.springclean.domain.usecase.exceptions.DomainException;

public class MakePaymentUsecase implements MakePayment {
  @Override
  public PaymentResult execute(Invoice invoice, Payment method) throws DomainException {
    return null;
  }
}
