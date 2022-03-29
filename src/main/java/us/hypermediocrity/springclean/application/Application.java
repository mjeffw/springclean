package us.hypermediocrity.springclean.application;

public interface Application {
  InvoiceVO viewInvoice(String invoiceId);

  void payBill(String invoiceId, PaymentVO payment);
}
