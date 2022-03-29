package us.hypermediocrity.springclean.usecase;

import us.hypermediocrity.springclean.application.PaymentVO;

public interface MakePayment {
  void execute(String invoiceId, PaymentVO payment);
}
