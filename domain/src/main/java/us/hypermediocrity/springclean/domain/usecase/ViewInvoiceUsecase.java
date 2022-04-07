package us.hypermediocrity.springclean.domain.usecase;

import us.hypermediocrity.springclean.domain.common.CustomerNotFoundException;
import us.hypermediocrity.springclean.domain.common.DomainException;
import us.hypermediocrity.springclean.domain.common.InvoiceInfo;
import us.hypermediocrity.springclean.domain.common.InvoiceNotFoundException;
import us.hypermediocrity.springclean.domain.entity.Customer;
import us.hypermediocrity.springclean.domain.entity.Invoice;
import us.hypermediocrity.springclean.domain.port.CurrencyExchangeService;
import us.hypermediocrity.springclean.domain.port.CustomerEntityGateway;
import us.hypermediocrity.springclean.domain.port.InvoiceEntityGateway;
import us.hypermediocrity.springclean.domain.utils.InvoiceInfoBuilder;

/**
 * Another class that is only public because of the configuration in
 * springclean.SpringCleanApplication
 * 
 * @author M. Jeff Wilson
 */
public class ViewInvoiceUsecase implements ViewInvoice {
  private final CurrencyExchangeService exchangePort;
  private final InvoiceEntityGateway invoices;
  private final CustomerEntityGateway customers;

  public ViewInvoiceUsecase(InvoiceEntityGateway invoicePort, CustomerEntityGateway customerPort, CurrencyExchangeService exchangePort) {
    this.invoices = invoicePort;
    this.customers = customerPort;
    this.exchangePort = exchangePort;
  }

  @Override
  public InvoiceInfo execute(String invoiceId) throws DomainException {
    Invoice invoice = invoices.getInvoice(invoiceId).orElseThrow(() -> new InvoiceNotFoundException(invoiceId));
    Customer customer = customers.getCustomer(invoice.customerId())
        .orElseThrow(() -> new CustomerNotFoundException(invoice.customerId()));
    invoice.customer(customer);

    var report = InvoiceInfoBuilder.newInstance(exchangePort).invoice(invoice).build();
    return report;
  }
}
