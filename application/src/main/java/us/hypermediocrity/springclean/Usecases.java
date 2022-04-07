package us.hypermediocrity.springclean;

import us.hypermediocrity.springclean.domain.usecase.MakePayment;
import us.hypermediocrity.springclean.domain.usecase.ViewInvoice;

public class Usecases {
  private ViewInvoice viewInvoice;
  private MakePayment makePayment;

  public Usecases(ViewInvoice viewInvoice, MakePayment makePayment) {
    this.viewInvoice = viewInvoice;
    this.makePayment = makePayment;
  }

  public ViewInvoice viewInvoice() {
    return this.viewInvoice;
  }

  public MakePayment makePayment() {
    return this.makePayment;
  }
}
