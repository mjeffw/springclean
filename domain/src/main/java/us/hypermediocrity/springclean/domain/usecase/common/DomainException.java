package us.hypermediocrity.springclean.domain.usecase.common;

public class DomainException extends Exception {
  private static final long serialVersionUID = 1L;

  public DomainException(String message) {
    super(message);
  }
}
