package us.hypermediocrity.springclean.domain.port;

import us.hypermediocrity.springclean.common.Money;

public interface PaymentService {
  PaymentResponse request(Money adjusted, PaymentInfo paymentInfo);
}
