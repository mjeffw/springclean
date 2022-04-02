package us.hypermediocrity.springclean.domain.port;

import java.util.Optional;

import us.hypermediocrity.springclean.domain.entity.Invoice;

public interface InvoicePort {
  Optional<Invoice> getInvoice(String invoiceId);
}
