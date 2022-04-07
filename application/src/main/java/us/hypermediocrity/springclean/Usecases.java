package us.hypermediocrity.springclean;

import us.hypermediocrity.springclean.domain.usecase.MakePayment;
import us.hypermediocrity.springclean.domain.usecase.ViewInvoice;

public record Usecases(ViewInvoice viewInvoice, MakePayment makePayment) {
}
