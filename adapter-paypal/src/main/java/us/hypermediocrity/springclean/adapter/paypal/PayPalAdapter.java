package us.hypermediocrity.springclean.adapter.paypal;

import us.hypermediocrity.springclean.common.Money;
import us.hypermediocrity.springclean.domain.port.PaymentInfo;
import us.hypermediocrity.springclean.domain.port.PaymentResponse;
import us.hypermediocrity.springclean.domain.port.PaymentService;

public class PayPalAdapter implements PaymentService {
  @Override
  public PaymentResponse request(Money adjusted, PaymentInfo paymentInfo) {
    // TODO Auto-generated method stub
    return null;
  }
}
