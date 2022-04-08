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
import us.hypermediocrity.springclean.common.Money;
import us.hypermediocrity.springclean.domain.port.PaymentInfo;
import us.hypermediocrity.springclean.domain.port.PaymentType;
import us.hypermediocrity.springclean.domain.usecase.common.DomainException;
import us.hypermediocrity.springclean.domain.usecase.common.InvoiceInfo;
import us.hypermediocrity.springclean.domain.usecase.common.InvoiceNotFoundException;
import us.hypermediocrity.springclean.domain.usecase.common.PaymentConfirmation;

@RestController
public class InvoiceController {
  @Autowired Usecases usecases;

  @GetMapping("/ping")
  public String ping() {
    return "running";
  }

  @GetMapping("/invoice/{id}")
  public InvoiceInfo viewInvoice(@PathVariable("id") String invoiceId) throws DomainException {
    return usecases.viewInvoice().execute(invoiceId);
  }

  @GetMapping("/invoice/pay/{id}/{payment}")
  public PaymentConfirmation payBill(@PathVariable("id") String invoiceId, @RequestBody PaymentRequest payment)
      throws DomainException {
    Money amount = new Money(payment.amount, Currency.getInstance(payment.currency));
    PaymentInfo paymentInfo = new PaymentInfo(PaymentType.valueOf(payment.type));
    return usecases.makePayment().execute(invoiceId, amount, paymentInfo);
  }

  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  @ExceptionHandler(InvoiceNotFoundException.class)
  public void invoiceNotFound() {
    // Nothing to do.
  }
}
