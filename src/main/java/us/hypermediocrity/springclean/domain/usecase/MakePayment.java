package us.hypermediocrity.springclean.domain.usecase;

import java.util.Properties;

import us.hypermediocrity.springclean.domain.entity.FundTransfer.Type;
import us.hypermediocrity.springclean.domain.entity.Money;
import us.hypermediocrity.springclean.domain.usecase.transfer.DomainException;
import us.hypermediocrity.springclean.domain.usecase.transfer.PaymentResult;

public interface MakePayment {
  PaymentResult execute(String invoiceId, Money amount, Type type, Properties details) throws DomainException;
}