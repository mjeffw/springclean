package us.hypermediocrity.springclean.adapter.restapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import us.hypermediocrity.springclean.application.Application;
import us.hypermediocrity.springclean.application.InvoiceVO;
import us.hypermediocrity.springclean.application.PaymentVO;
import us.hypermediocrity.springclean.domain.usecase.exceptions.DomainException;
import us.hypermediocrity.springclean.domain.usecase.exceptions.InvoiceNotFoundException;

@RestController
public class BillingController {
  @Autowired
  Application app;

  @GetMapping("/invoice/{id}")
  public InvoiceVO viewInvoice(@PathVariable("id") String invoiceId) throws DomainException {
    return app.viewInvoice(invoiceId);
  }

  @GetMapping("/billpay/{id}/{payment}")
  public void payBill(@PathVariable("id") String invoiceId, @RequestBody PaymentVO payment) throws DomainException {
    app.payBill(invoiceId, payment);
  }

  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  @ExceptionHandler(InvoiceNotFoundException.class)
  public void invoiceNotFound() {
    // Nothing to do.
  }
}
