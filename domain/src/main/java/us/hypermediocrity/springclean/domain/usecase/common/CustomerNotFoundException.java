package us.hypermediocrity.springclean.domain.usecase.common;

public class CustomerNotFoundException extends DomainException {
  private static final long serialVersionUID = 1L;

  public CustomerNotFoundException(String customerId) {
    super(customerId);
  }
}
