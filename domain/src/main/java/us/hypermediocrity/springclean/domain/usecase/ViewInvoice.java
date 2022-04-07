package us.hypermediocrity.springclean.domain.usecase;

import us.hypermediocrity.springclean.domain.common.DomainException;
import us.hypermediocrity.springclean.domain.common.InvoiceInfo;

public interface ViewInvoice {

  InvoiceInfo execute(String invoiceId) throws DomainException;

}