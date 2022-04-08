package us.hypermediocrity.springclean.adapter.paymentrouter;

import java.util.EnumMap;

import us.hypermediocrity.springclean.common.Money;
import us.hypermediocrity.springclean.domain.port.PaymentInfo;
import us.hypermediocrity.springclean.domain.port.PaymentResponse;
import us.hypermediocrity.springclean.domain.port.PaymentService;
import us.hypermediocrity.springclean.domain.port.PaymentType;

public class MoneyTransferPortRouter implements PaymentService {
  private EnumMap<PaymentType, PaymentService> map = new EnumMap<>(PaymentType.class);

  public MoneyTransferPortRouter(PaymentService payPalPort, PaymentService bankAccountPort,
      PaymentService creditCardPort) {
    map.put(PaymentType.BankTransfer, bankAccountPort);
    map.put(PaymentType.CreditCard, creditCardPort);
    map.put(PaymentType.PayPal, payPalPort);
  }

  @Override
  public PaymentResponse request(Money adjusted, PaymentInfo paymentInfo) {
    return map.get(paymentInfo.type()).request(adjusted, paymentInfo);
  }
}