package us.hypermediocrity.springclean.domain.port;

import us.hypermediocrity.springclean.domain.usecase.transfer.Status;

public record TransferResponse(Status status) {
}