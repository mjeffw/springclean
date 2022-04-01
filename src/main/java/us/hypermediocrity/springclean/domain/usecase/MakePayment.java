package us.hypermediocrity.springclean.domain.usecase;

import us.hypermediocrity.springclean.domain.DomainException;
import us.hypermediocrity.springclean.domain.entity.Invoice;
import us.hypermediocrity.springclean.domain.entity.Money;
import us.hypermediocrity.springclean.domain.entity.Payment;
import us.hypermediocrity.springclean.domain.entity.PaymentResult;
import us.hypermediocrity.springclean.domain.entity.PaymentResult.Reason;
import us.hypermediocrity.springclean.domain.port.CurrencyExchangePort;
import us.hypermediocrity.springclean.domain.port.MoneyTransferPort;
import us.hypermediocrity.springclean.domain.port.TransferResponse;

public class MakePayment {
  private final CurrencyExchangePort exchangePort;
  private final MoneyTransferPort transferPort;

  public MakePayment(CurrencyExchangePort exchangePort, MoneyTransferPort transferPort) {
    this.exchangePort = exchangePort;
    this.transferPort = transferPort;
  }

  public PaymentResult execute(Invoice invoice, Payment method) throws DomainException {
    var adjusted = adjustAmountForCurrency(method);

    // check payment against amount due
    if (isNothingDue(invoice)) {
      return nothingDue(invoice, adjusted);
    } else if (isNotPaidInFull(invoice, adjusted)) {
      return notPaidInFull(invoice, adjusted);
    } else if (isOverpayment(invoice, adjusted)) {
      return overPaid(invoice, adjusted);
    }

    // Accept payment/funds transfer
    var response = transferPort.request(adjusted.amount(), adjusted.transfer());

    if (response.status() == TransferResponse.Status.OK)
      return paidInFull(invoice, method);

    return transactionFailed(invoice, method, response);
  }

  private Payment adjustAmountForCurrency(Payment method) {
    if (method.amount().currency == Money.USD) {
      return method;
    }

    var money = exchangePort.convert(method.amount(), Money.USD);
    return new Payment(money, method.transfer());
  }

  private PaymentResult transactionFailed(Invoice invoice, Payment method, TransferResponse response) {
    return new PaymentResult(Money.ZERO, Reason.TRANSACTION_FAILED);
  }

  public boolean isOverpayment(Invoice invoice, Payment method) {
    return invoice.total().lessThan(method.amount());
  }

  public boolean isNotPaidInFull(Invoice invoice, Payment method) {
    return invoice.total().greaterThan(method.amount());
  }

  public boolean isNothingDue(Invoice invoice) {
    return invoice.total().equals(Money.ZERO);
  }

  public PaymentResult overPaid(Invoice invoice, Payment method) {
    return new PaymentResult(Money.ZERO, Reason.OVERPAID);
  }

  public PaymentResult paidInFull(Invoice invoice, Payment method) {
    return new PaymentResult(method.amount(), Reason.PAID_IN_FULL);
  }

  public PaymentResult notPaidInFull(Invoice invoice, Payment method) {
    return new PaymentResult(Money.ZERO, Reason.NOT_PAID_IN_FULL);
  }

  public PaymentResult nothingDue(Invoice invoice, Payment method) {
    return new PaymentResult(Money.ZERO, Reason.AMOUNT_DUE_IS_ZERO);
  }
}
