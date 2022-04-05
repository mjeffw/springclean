package us.hypermediocrity.springclean.domain.usecase;

import us.hypermediocrity.springclean.domain.usecase.transfer.DomainException;
import us.hypermediocrity.springclean.domain.usecase.transfer.InvoiceView;

public interface ViewInvoice {
  InvoiceView execute(String invoiceId) throws DomainException;
}