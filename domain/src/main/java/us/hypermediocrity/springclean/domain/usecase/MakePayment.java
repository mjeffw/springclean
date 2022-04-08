package us.hypermediocrity.springclean.domain.usecase;

import us.hypermediocrity.springclean.common.Money;
import us.hypermediocrity.springclean.domain.common.DomainException;
import us.hypermediocrity.springclean.domain.common.PaymentConfirmation;
import us.hypermediocrity.springclean.domain.port.PaymentInfo;

public interface MakePayment extends Usecase {
  PaymentConfirmation execute(String invoiceId, Money paymentAmount, PaymentInfo paymentInfo) throws DomainException;
}