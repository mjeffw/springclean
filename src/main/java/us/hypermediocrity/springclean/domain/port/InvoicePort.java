package us.hypermediocrity.springclean.domain.port;

import us.hypermediocrity.springclean.domain.entity.Invoice;

public interface InvoicePort {

  Invoice getInvoice(String invoiceId);

}
