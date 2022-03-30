package us.hypermediocrity.springclean.adapter.invoice;

import java.util.Optional;

import us.hypermediocrity.springclean.domain.entity.Invoice;
import us.hypermediocrity.springclean.domain.port.InvoicePort;

public class InvoicePortAdapter implements InvoicePort {

  @Override
  public Optional<Invoice> getInvoice(String invoiceId) {
    // TODO Auto-generated method stub
    return null;
  }

}
