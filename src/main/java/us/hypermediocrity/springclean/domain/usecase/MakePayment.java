package us.hypermediocrity.springclean.domain.usecase;

import java.util.Currency;

import us.hypermediocrity.springclean.domain.DomainException;
import us.hypermediocrity.springclean.domain.entity.Invoice;
import us.hypermediocrity.springclean.domain.entity.Money;
import us.hypermediocrity.springclean.domain.entity.Payment;
import us.hypermediocrity.springclean.domain.entity.PaymentResult;

public class MakePayment {
  public static final Currency USD = Currency.getInstance("USD");

  public PaymentResult execute(Invoice invoice, Payment method) throws DomainException {
    // check payment against amount due
    if (!invoice.total().greaterThan(Money.ZERO)) {
      return new PaymentResult(Money.ZERO, PaymentResult.Reason.AMOUNT_DUE_IS_ZERO);
    }

    return new PaymentResult(Money.ZERO, null);
  }
}
