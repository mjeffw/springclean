package us.hypermediocrity.springclean.domain.port;

import us.hypermediocrity.springclean.domain.entity.FundTransfer;
import us.hypermediocrity.springclean.domain.entity.Money;

public interface MoneyTransferPort {
  TransferResponse request(Money amount, FundTransfer transfer);
}
