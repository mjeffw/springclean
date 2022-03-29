package us.hypermediocrity.springclean.domain.usecase;

import org.springframework.beans.factory.annotation.Autowired;

import us.hypermediocrity.springclean.domain.entity.Customer;
import us.hypermediocrity.springclean.domain.entity.Invoice;
import us.hypermediocrity.springclean.domain.port.CurrencyExchangePort;
import us.hypermediocrity.springclean.domain.port.CustomerPort;
import us.hypermediocrity.springclean.domain.port.InvoiceViewBuilder;
import us.hypermediocrity.springclean.domain.port.InvoicePort;
import us.hypermediocrity.springclean.domain.port.InvoiceView;

public class ViewInvoiceImpl implements ViewInvoice {
  @Autowired
  private InvoicePort invoicePort;

  @Autowired
  private CustomerPort customerPort;

  @Autowired
  CurrencyExchangePort exchangePort;

  @Override
  public InvoiceView execute(String invoiceId) {

    Invoice invoice = invoicePort.getInvoice(invoiceId);
    Customer customer = customerPort.getCustomer(invoice.getCustomerId());

    InvoiceView report = InvoiceViewBuilder.newInstance(exchangePort).invoice(invoice).customer(customer).build();

    return report;
  }

}
