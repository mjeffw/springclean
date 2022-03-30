package us.hypermediocrity.springclean.domain.usecase;

import us.hypermediocrity.springclean.domain.entity.Invoice;
import us.hypermediocrity.springclean.domain.entity.Payment;
import us.hypermediocrity.springclean.domain.entity.PaymentResult;
import us.hypermediocrity.springclean.domain.usecase.exceptions.DomainException;

public interface MakePayment {
  PaymentResult execute(Invoice invoice, Payment payment) throws DomainException;
}
