package us.hypermediocrity.springclean.domain.port;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import us.hypermediocrity.springclean.domain.entity.Invoice;

public interface Invoices {
  Optional<Invoice> getInvoice(String invoiceId);

  void update(Invoice invoice);

  List<Invoice> findInvoicesDueBefore(Date date);

  List<Invoice> findOpenInvoicesByCustomer(String customerId);

  void create(Invoice invoice);
}
