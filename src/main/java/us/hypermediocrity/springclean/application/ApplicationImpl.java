package us.hypermediocrity.springclean.application;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

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
  @Autowired
  MakePayment makePayment;

  @Autowired
  ViewInvoice viewInvoice;

  private ModelMapper modelMapper = new ModelMapper();

  @Override
  public InvoiceVO viewInvoice(String invoiceId) {
    InvoiceView invoice = viewInvoice.execute(invoiceId);
    return convertInvoice(invoice);
  }

  @Override
  public void payBill(String invoiceId, PaymentVO payment) {
    makePayment.execute(invoiceId, payment);
  }

  private InvoiceVO convertInvoice(InvoiceView invoice) {
    return modelMapper.map(invoice, InvoiceVO.class);
  }
}
