package us.hypermediocrity.springclean.domain.usecase;

import us.hypermediocrity.springclean.domain.usecase.common.DomainException;
import us.hypermediocrity.springclean.domain.usecase.common.InvoiceInfo;

public interface ViewInvoice extends Usecase {
  InvoiceInfo execute(String invoiceId) throws DomainException;
}