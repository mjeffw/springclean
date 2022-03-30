package us.hypermediocrity.springclean.domain.usecase;

import us.hypermediocrity.springclean.domain.entity.Invoice;
import us.hypermediocrity.springclean.domain.port.InvoiceView;
import us.hypermediocrity.springclean.domain.usecase.exceptions.DomainException;

public interface ViewInvoice {
  InvoiceView execute(Invoice invoice) throws DomainException;
}
