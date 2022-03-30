package us.hypermediocrity.springclean.domain.usecase;

import us.hypermediocrity.springclean.domain.entity.Customer;
import us.hypermediocrity.springclean.domain.entity.Invoice;
import us.hypermediocrity.springclean.domain.port.CurrencyExchangePort;
import us.hypermediocrity.springclean.domain.port.CustomerPort;
import us.hypermediocrity.springclean.domain.port.InvoicePort;
import us.hypermediocrity.springclean.domain.port.InvoiceView;
import us.hypermediocrity.springclean.domain.port.InvoiceViewBuilder;

/**
 * Another class that is only public because of the configuration in
 * springclean.SpringCleanApplication
 * 
 * @author jw9615
 *
 */
public class ViewInvoiceImpl implements ViewInvoice {
  private final InvoicePort invoicePort;
  private final CustomerPort customerPort;
  private final CurrencyExchangePort exchangePort;

  public ViewInvoiceImpl(InvoicePort invoicePort, CustomerPort customerPort, CurrencyExchangePort exchangePort) {
    this.invoicePort = invoicePort;
    this.customerPort = customerPort;
    this.exchangePort = exchangePort;
  }

  @Override
  public InvoiceView execute(String invoiceId) throws DomainException {

    Invoice invoice = invoicePort.getInvoice(invoiceId);
    if (invoice == null) {
      throw new InvoiceNotFoundException(invoiceId);
    }

    Customer customer = customerPort.getCustomer(invoice.customerId());

    // I could have done this mapping in the Application layer -- one of its
    // responsibilities is to map values between the adapters and the domain.
    //
    // Where you do the mapping depends on whether the mapping is specified by
    // business rules -- if that answer is 'yes', then the mapping properly belongs
    // inside the usecase.
    InvoiceView report = InvoiceViewBuilder.newInstance(exchangePort).invoice(invoice).customer(customer).build();

    return report;
  }
}
