package us.hypermediocrity.springclean.domain.port;

public class TransferResponse {
  public enum Status {
    FAILED, OK
  }

  private final Status status;

  public TransferResponse(Status status) {
    this.status = status;
  }

  public Status status() {
    return status;
  }
}