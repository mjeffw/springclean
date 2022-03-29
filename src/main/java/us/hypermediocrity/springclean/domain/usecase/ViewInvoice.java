package us.hypermediocrity.springclean.domain.usecase;

import us.hypermediocrity.springclean.domain.entity.Invoice;

public interface ViewInvoice {
  Invoice execute(String invoiceId);
}
