package us.hypermediocrity.springclean.domain.usecase;

import java.util.Properties;

import us.hypermediocrity.springclean.domain.entity.FundTransfer;
import us.hypermediocrity.springclean.domain.entity.FundTransfer.Type;
import us.hypermediocrity.springclean.domain.entity.Invoice;
import us.hypermediocrity.springclean.domain.entity.Money;
import us.hypermediocrity.springclean.domain.entity.Payment;
import us.hypermediocrity.springclean.domain.port.CurrencyExchangePort;
import us.hypermediocrity.springclean.domain.port.InvoicePort;
import us.hypermediocrity.springclean.domain.port.MoneyTransferPort;
import us.hypermediocrity.springclean.domain.port.TransferResponse;
import us.hypermediocrity.springclean.domain.usecase.transfer.DomainException;
import us.hypermediocrity.springclean.domain.usecase.transfer.InvoiceNotFoundException;
import us.hypermediocrity.springclean.domain.usecase.transfer.PaymentResult;
import us.hypermediocrity.springclean.domain.usecase.transfer.Reason;
import us.hypermediocrity.springclean.domain.usecase.transfer.Status;

public class MakePaymentUsecase implements MakePayment {
  private final CurrencyExchangePort exchangePort;
  private final MoneyTransferPort transferPort;
  private final InvoicePort invoicePort;

  public MakePaymentUsecase(InvoicePort invoicePort, CurrencyExchangePort exchangePort,
      MoneyTransferPort transferPort) {
    this.invoicePort = invoicePort;
    this.exchangePort = exchangePort;
    this.transferPort = transferPort;
  }

  @Override
  public PaymentResult execute(String invoiceId, Money amount, Type type, Properties details) throws DomainException {
    Invoice invoice = invoicePort.getInvoice(invoiceId).orElseThrow(() -> new InvoiceNotFoundException(invoiceId));
    Payment payment = new Payment(amount, new FundTransfer(type, details));
    return execute(invoice, payment);
  }

  PaymentResult execute(Invoice invoice, Payment method) throws DomainException {
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

    if (response.status() == Status.OK)
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

  private boolean isOverpayment(Invoice invoice, Payment method) {
    return invoice.total().lessThan(method.amount());
  }

  private boolean isNotPaidInFull(Invoice invoice, Payment method) {
    return invoice.total().greaterThan(method.amount());
  }

  private boolean isNothingDue(Invoice invoice) {
    return invoice.total().equals(Money.ZERO);
  }

  private PaymentResult overPaid(Invoice invoice, Payment method) {
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
