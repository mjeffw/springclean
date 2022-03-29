package us.hypermediocrity.springclean.adapter.restapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import us.hypermediocrity.springclean.application.Application;
import us.hypermediocrity.springclean.application.InvoiceVO;
import us.hypermediocrity.springclean.application.PaymentVO;

@RestController
public class BillingController {
  @Autowired
  Application app;

  @GetMapping("/invoice/{id}")
  public InvoiceVO viewInvoice(@PathVariable("id") String invoiceId) {
    return app.viewInvoice(invoiceId);
  }

  @GetMapping("/billpay/{id}/{payment}")
  public void payBill(@PathVariable("id") String invoiceId, @RequestBody PaymentVO payment) {
    app.payBill(invoiceId, payment);
  }
}
