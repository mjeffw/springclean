package us.hypermediocrity.springclean.domain.usecase;

import us.hypermediocrity.springclean.common.Money;
import us.hypermediocrity.springclean.domain.port.PaymentInfo;
import us.hypermediocrity.springclean.domain.usecase.common.DomainException;
import us.hypermediocrity.springclean.domain.usecase.common.PaymentConfirmation;

public interface MakePayment extends Usecase {
  PaymentConfirmation execute(String invoiceId, Money paymentAmount, PaymentInfo paymentInfo) throws DomainException;
}