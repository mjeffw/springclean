package us.hypermediocrity.springclean.domain.usecase;

import us.hypermediocrity.springclean.domain.port.InvoiceView;

public interface ViewInvoice {
  InvoiceView execute(String invoiceId);
}
