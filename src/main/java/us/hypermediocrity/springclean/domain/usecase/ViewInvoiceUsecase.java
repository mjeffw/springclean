package us.hypermediocrity.springclean.domain.usecase;

import us.hypermediocrity.springclean.domain.entity.Customer;
import us.hypermediocrity.springclean.domain.entity.Invoice;
import us.hypermediocrity.springclean.domain.port.CurrencyExchangePort;
import us.hypermediocrity.springclean.domain.port.CustomerPort;
import us.hypermediocrity.springclean.domain.port.InvoicePort;
import us.hypermediocrity.springclean.domain.usecase.transfer.CustomerNotFoundException;
import us.hypermediocrity.springclean.domain.usecase.transfer.DomainException;
import us.hypermediocrity.springclean.domain.usecase.transfer.InvoiceNotFoundException;
import us.hypermediocrity.springclean.domain.usecase.transfer.InvoiceView;
import us.hypermediocrity.springclean.domain.usecase.transfer.InvoiceViewBuilder;

/**
 * Another class that is only public because of the configuration in
 * springclean.SpringCleanApplication
 * 
 * @author jw9615
 *
 */
public class ViewInvoiceUsecase implements ViewInvoice {
  private final CurrencyExchangePort exchangePort;
  private final InvoicePort invoicePort;
  private final CustomerPort customerPort;

  public ViewInvoiceUsecase(InvoicePort invoicePort, CustomerPort customerPort, CurrencyExchangePort exchangePort) {
    this.invoicePort = invoicePort;
    this.customerPort = customerPort;
    this.exchangePort = exchangePort;
  }

  @Override
  public InvoiceView execute(String invoiceId) throws DomainException {
    Invoice invoice = invoicePort.getInvoice(invoiceId).orElseThrow(() -> new InvoiceNotFoundException(invoiceId));
    Customer customer = customerPort.getCustomer(invoice.customerId())
        .orElseThrow(() -> new CustomerNotFoundException(invoice.customerId()));
    invoice.customer(customer);

    return execute(invoice);
  }

  public InvoiceView execute(Invoice invoice) throws DomainException {
    var report = InvoiceViewBuilder.newInstance(exchangePort).invoice(invoice).build();
    return report;
  }
}
