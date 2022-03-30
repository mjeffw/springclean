package us.hypermediocrity.springclean.application;

import us.hypermediocrity.springclean.domain.usecase.DomainException;

public interface Application {
  InvoiceVO viewInvoice(String invoiceId) throws DomainException;

  void payBill(String invoiceId, PaymentVO payment);
}
