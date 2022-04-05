package us.hypermediocrity.springclean;

import us.hypermediocrity.springclean.domain.port.CurrencyExchangePort;
import us.hypermediocrity.springclean.domain.port.CustomerPort;
import us.hypermediocrity.springclean.domain.port.InvoicePort;
import us.hypermediocrity.springclean.domain.port.MoneyTransferPort;
import us.hypermediocrity.springclean.domain.usecase.MakePayment;
import us.hypermediocrity.springclean.domain.usecase.MakePaymentUsecase;
import us.hypermediocrity.springclean.domain.usecase.ViewInvoice;
import us.hypermediocrity.springclean.domain.usecase.ViewInvoiceUsecase;

public class Usecases {
  private CurrencyExchangePort exchangePort;

  private MoneyTransferPort transferPort;

  private InvoicePort invoicePort;

  private CustomerPort customerPort;

  public Usecases(CurrencyExchangePort exchangePort, InvoicePort invoicePort, CustomerPort customerPort,
      MoneyTransferPort transferPort) {
    this.exchangePort = exchangePort;
    this.invoicePort = invoicePort;
    this.customerPort = customerPort;
    this.transferPort = transferPort;
  }

  public MakePayment makePayment() {
    return new MakePaymentUsecase(invoicePort, exchangePort, transferPort);
  }

  public ViewInvoice viewInvoice() {
    return new ViewInvoiceUsecase(invoicePort, customerPort, exchangePort);
  }
}
