package us.hypermediocrity.springclean.domain.usecase;

import us.hypermediocrity.springclean.domain.entity.Invoice;
import us.hypermediocrity.springclean.domain.port.CurrencyExchangePort;
import us.hypermediocrity.springclean.domain.port.InvoiceView;
import us.hypermediocrity.springclean.domain.port.InvoiceViewBuilder;
import us.hypermediocrity.springclean.domain.usecase.exceptions.DomainException;

/**
 * Another class that is only public because of the configuration in
 * springclean.SpringCleanApplication
 * 
 * @author jw9615
 *
 */
public class ViewInvoiceUsecase implements ViewInvoice {
  private final CurrencyExchangePort exchangePort;

  public ViewInvoiceUsecase(CurrencyExchangePort exchangePort) {
    this.exchangePort = exchangePort;
  }

  @Override
  public InvoiceView execute(Invoice invoice) throws DomainException {

    // I could have done this mapping in the Application layer -- one of its
    // responsibilities is to map values between the adapters and the domain.
    //
    // Where you do the mapping depends on whether the mapping is specified by
    // business rules -- if that answer is 'yes', then the mapping properly belongs
    // inside the usecase.
    var report = InvoiceViewBuilder.newInstance(exchangePort).invoice(invoice).build();

    return report;
  }
}
