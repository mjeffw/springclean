package us.hypermediocrity.springclean.domain.usecase;

import us.hypermediocrity.springclean.domain.port.InvoiceView;
import us.hypermediocrity.springclean.domain.usecase.exceptions.DomainException;

public interface ViewInvoice {
  InvoiceView execute(String invoiceId) throws DomainException;
}
