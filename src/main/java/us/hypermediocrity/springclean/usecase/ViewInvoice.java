package us.hypermediocrity.springclean.usecase;

import us.hypermediocrity.springclean.domain.Invoice;

public interface ViewInvoice {
  Invoice execute(String invoiceId);
}
