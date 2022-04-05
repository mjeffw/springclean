package us.hypermediocrity.springclean.adapter.restapi;

import java.util.Currency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import us.hypermediocrity.springclean.Usecases;
import us.hypermediocrity.springclean.domain.entity.FundTransfer.Type;
import us.hypermediocrity.springclean.domain.entity.Money;
import us.hypermediocrity.springclean.domain.usecase.transfer.DomainException;
import us.hypermediocrity.springclean.domain.usecase.transfer.InvoiceNotFoundException;
import us.hypermediocrity.springclean.domain.usecase.transfer.InvoiceView;

@RestController
public class BillingController {
  @Autowired
  Usecases app;

  @GetMapping("/invoice/{id}")
  public InvoiceView viewInvoice(@PathVariable("id") String invoiceId) throws DomainException {
    return app.viewInvoice().execute(invoiceId);
  }

  @GetMapping("/invoice/pay/{id}/{payment}")
  public void payBill(@PathVariable("id") String invoiceId, @RequestBody PaymentRequest payment)
      throws DomainException {
    Money amount = new Money(payment.amount, Currency.getInstance(payment.currency));
    Type type = Type.valueOf(payment.type);
    app.makePayment().execute(invoiceId, amount, type, payment.details);
  }

  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  @ExceptionHandler(InvoiceNotFoundException.class)
  public void invoiceNotFound() {
    // Nothing to do.
  }
}
