package us.hypermediocrity.springclean.domain.port;

import us.hypermediocrity.springclean.common.Money;
import us.hypermediocrity.springclean.domain.common.PaymentInfo;

public interface PaymentService {
  PaymentResponse request(Money adjusted, PaymentInfo paymentInfo);
}
