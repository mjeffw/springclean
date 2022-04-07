package us.hypermediocrity.springclean.adapter.invoice;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import us.hypermediocrity.springclean.domain.entity.Invoice;
import us.hypermediocrity.springclean.domain.port.Invoices;

public class InvoicesAdapter implements Invoices {
  @Override
  public Optional<Invoice> getInvoice(String invoiceId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void update(Invoice invoice) {
    // TODO Auto-generated method stub
  }

  @Override
  public List<Invoice> findInvoicesDueBefore(Date date) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<Invoice> findOpenInvoicesByCustomer(String customerId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void create(Invoice invoice) {
    // TODO Auto-generated method stub
  }
}
