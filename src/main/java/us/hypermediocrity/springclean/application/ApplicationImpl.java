package us.hypermediocrity.springclean.application;

import org.modelmapper.ModelMapper;

import us.hypermediocrity.springclean.domain.entity.FundTransfer;
import us.hypermediocrity.springclean.domain.entity.Invoice;
import us.hypermediocrity.springclean.domain.entity.Money;
import us.hypermediocrity.springclean.domain.entity.Payment;
import us.hypermediocrity.springclean.domain.port.CustomerPort;
import us.hypermediocrity.springclean.domain.port.InvoicePort;
import us.hypermediocrity.springclean.domain.port.InvoiceView;
import us.hypermediocrity.springclean.domain.usecase.MakePayment;
import us.hypermediocrity.springclean.domain.usecase.ViewInvoice;
import us.hypermediocrity.springclean.domain.usecase.exceptions.DomainException;
import us.hypermediocrity.springclean.domain.usecase.exceptions.InvoiceNotFoundException;

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
  private ViewInvoice viewInvoice;
  private MakePayment makePayment;

  public ApplicationImpl(ViewInvoice viewInvoice, MakePayment makePayment, InvoicePort invoicePort,
      CustomerPort customerPort) {
    this.viewInvoice = viewInvoice;
    this.makePayment = makePayment;
    this.invoicePort = invoicePort;
    this.customerPort = customerPort;
  }

  @Override
  public InvoiceVO viewInvoice(String invoiceId) throws DomainException {
    Invoice invoice = getInvoice(invoiceId);

    InvoiceView report = viewInvoice.execute(invoice);
    return convertInvoice(report);
  }

  @Override
  public void payBill(String invoiceId, PaymentVO paymentVO) throws DomainException {
    Invoice invoice = getInvoice(invoiceId);
    Payment payment = new Payment(new Money(paymentVO.amount(), invoice.currency()), create(paymentVO));

    makePayment.execute(invoice, payment);
  }

  private Invoice getInvoice(String invoiceId) throws DomainException {
    Invoice invoice = invoicePort.getInvoice(invoiceId).orElseThrow(() -> new InvoiceNotFoundException(invoiceId));
    invoice.customer(customerPort.getCustomer(invoice.customerId()));
    return invoice;
  }

  private InvoiceVO convertInvoice(InvoiceView invoice) {
    return modelMapper.map(invoice, InvoiceVO.class);
  }

  private FundTransfer create(PaymentVO payment) {
    FundTransfer.Type type = FundTransfer.Type.valueOf(payment.type());
    return new FundTransfer(type, payment.properties());
  }
}
