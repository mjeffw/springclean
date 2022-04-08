package us.hypermediocrity.springclean.domain.usecase;

import us.hypermediocrity.springclean.domain.common.DomainException;
import us.hypermediocrity.springclean.domain.common.InvoiceInfo;

public interface ViewInvoice extends Usecase {
  InvoiceInfo execute(String invoiceId) throws DomainException;
}