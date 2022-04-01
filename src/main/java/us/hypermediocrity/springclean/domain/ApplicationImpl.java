package us.hypermediocrity.springclean.domain;

import java.util.Currency;
import java.util.Properties;

import org.modelmapper.ModelMapper;

import us.hypermediocrity.springclean.domain.entity.FundTransfer;
import us.hypermediocrity.springclean.domain.entity.Invoice;
import us.hypermediocrity.springclean.domain.entity.Money;
import us.hypermediocrity.springclean.domain.entity.Payment;
import us.hypermediocrity.springclean.domain.entity.PaymentResult;
import us.hypermediocrity.springclean.domain.port.CurrencyExchangePort;
import us.hypermediocrity.springclean.domain.port.CustomerPort;
import us.hypermediocrity.springclean.domain.port.InvoicePort;
import us.hypermediocrity.springclean.domain.port.InvoiceView;
import us.hypermediocrity.springclean.domain.usecase.MakePayment;
import us.hypermediocrity.springclean.domain.usecase.ViewInvoice;

/**
 * I don't like making this public. It's only public so that the SprintBoot
 * configuration in springclean.SpringCleanApplication can access it.
 * 
 * Look into fixing this with Java Jigsaw modules.
 * 
 * @author jw9615
 */
public class ApplicationImpl implements Application {
  private ModelMapper modelMapper = new ModelMapper();
  private InvoicePort invoicePort;
  private CustomerPort customerPort;
  private CurrencyExchangePort exchangePort;

  public ApplicationImpl(CurrencyExchangePort exchangePort, InvoicePort invoicePort, CustomerPort customerPort) {
    this.exchangePort = exchangePort;
    this.invoicePort = invoicePort;
    this.customerPort = customerPort;
  }

  @Override
  public InvoiceVO viewInvoice(String invoiceId) throws DomainException {
    Invoice invoice = getInvoice(invoiceId);

    InvoiceView report = new ViewInvoice(exchangePort).execute(invoice);
    return convertInvoice(report);
  }

  @Override
  public PaymentResultVO payBill(String invoiceId, double amount, String currencyCode, String paymentType,
      Properties paymentDetails) throws DomainException {
    Invoice invoice = getInvoice(invoiceId);
    Payment payment = new Payment(new Money(amount, Currency.getInstance(currencyCode)),
        create(paymentType, paymentDetails));

    var result = new MakePayment().execute(invoice, payment);
    return convertPaymentResult(result);
  }

  private PaymentResultVO convertPaymentResult(PaymentResult result) {
    return modelMapper.map(result, PaymentResultVO.class);
  }

  private Invoice getInvoice(String invoiceId) throws DomainException {
    Invoice invoice = invoicePort.getInvoice(invoiceId).orElseThrow(() -> new InvoiceNotFoundException(invoiceId));
    invoice.customer(customerPort.getCustomer(invoice.customerId()));
    return invoice;
  }

  private InvoiceVO convertInvoice(InvoiceView invoice) {
    return modelMapper.map(invoice, InvoiceVO.class);
  }

  private FundTransfer create(String paymentType, Properties paymentDetails) {
    FundTransfer.Type type = FundTransfer.Type.valueOf(paymentType);
    return new FundTransfer(type, paymentDetails);
  }
}
