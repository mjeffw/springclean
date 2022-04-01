package us.hypermediocrity.springclean.adapter.paypal;

import us.hypermediocrity.springclean.domain.entity.FundTransfer;
import us.hypermediocrity.springclean.domain.entity.Money;
import us.hypermediocrity.springclean.domain.port.MoneyTransferPort;
import us.hypermediocrity.springclean.domain.port.TransferResponse;

public class PayPalAdapter implements MoneyTransferPort {

  @Override
  public TransferResponse request(Money amount, FundTransfer transfer) {
    // TODO Auto-generated method stub
    return null;
  }

}
