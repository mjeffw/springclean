package us.hypermediocrity.springclean.domain.usecase.exceptions;

public class InvoiceNotFoundException extends DomainException {
  private static final long serialVersionUID = 1L;

  public InvoiceNotFoundException(String invoiceId) {
    super(invoiceId);
  }
}
