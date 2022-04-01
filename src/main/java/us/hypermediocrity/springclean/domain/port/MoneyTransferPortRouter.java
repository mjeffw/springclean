package us.hypermediocrity.springclean.domain.port;

import java.util.EnumMap;

import us.hypermediocrity.springclean.domain.entity.FundTransfer;
import us.hypermediocrity.springclean.domain.entity.FundTransfer.Type;
import us.hypermediocrity.springclean.domain.entity.Money;

public class MoneyTransferPortRouter implements MoneyTransferPort {
  private EnumMap<Type, MoneyTransferPort> map = new EnumMap<>(Type.class);

  public MoneyTransferPortRouter(MoneyTransferPort payPalPort, MoneyTransferPort bankAccountPort,
      MoneyTransferPort creditCardPort) {
    map.put(Type.BankTransfer, bankAccountPort);
    map.put(Type.CreditCard, creditCardPort);
    map.put(Type.PayPal, payPalPort);
  }

  @Override
  public TransferResponse request(Money amount, FundTransfer transfer) {
    return null;
  }
}
