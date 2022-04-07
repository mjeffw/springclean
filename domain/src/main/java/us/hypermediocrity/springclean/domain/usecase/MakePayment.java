package us.hypermediocrity.springclean.domain.usecase;

import us.hypermediocrity.springclean.common.Money;
import us.hypermediocrity.springclean.domain.common.DomainException;
import us.hypermediocrity.springclean.domain.common.PaymentConfirmation;
import us.hypermediocrity.springclean.domain.common.PaymentInfo;

public interface MakePayment {

  PaymentConfirmation execute(String invoiceId, Money paymentAmount, PaymentInfo paymentInfo) throws DomainException;

}