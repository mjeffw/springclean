package us.hypermediocrity.springclean.domain.usecase;

public class DomainException extends Exception {
  private static final long serialVersionUID = 1L;

  public DomainException(String message) {
    super(message);
  }
}
