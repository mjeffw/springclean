package us.hypermediocrity.springclean.domain;

import java.util.Properties;

import us.hypermediocrity.springclean.domain.usecase.DomainException;

public interface Application {
  InvoiceVO viewInvoice(String invoiceId) throws DomainException;

  PaymentResultVO payBill(String invoiceId, double amount, String currencyCode, String paymentType,
      Properties paymentDetails) throws DomainException;
}
