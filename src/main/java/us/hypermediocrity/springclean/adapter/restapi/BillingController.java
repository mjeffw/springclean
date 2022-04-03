package us.hypermediocrity.springclean.adapter.restapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import us.hypermediocrity.springclean.domain.Application;
import us.hypermediocrity.springclean.domain.InvoiceVO;
import us.hypermediocrity.springclean.domain.usecase.DomainException;
import us.hypermediocrity.springclean.domain.usecase.InvoiceNotFoundException;

@RestController
public class BillingController {
  @Autowired
  Application app;

  @GetMapping("/invoice/{id}")
  public InvoiceVO viewInvoice(@PathVariable("id") String invoiceId) throws DomainException {
    return app.viewInvoice(invoiceId);
  }

  @GetMapping("/billpay/{id}/{payment}")
  public void payBill(@PathVariable("id") String invoiceId, @RequestBody PaymentRequest payment)
      throws DomainException {
    app.payBill(invoiceId, payment.amount, payment.currency, payment.type, payment.details);
  }

  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  @ExceptionHandler(InvoiceNotFoundException.class)
  public void invoiceNotFound() {
    // Nothing to do.
  }
}
