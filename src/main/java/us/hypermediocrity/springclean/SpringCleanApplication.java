package us.hypermediocrity.springclean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import us.hypermediocrity.springclean.adapter.currencyconvert.CurrencyConverterAdapter;
import us.hypermediocrity.springclean.adapter.customer.CustomerPortAdapter;
import us.hypermediocrity.springclean.adapter.invoice.InvoicePortAdapter;
import us.hypermediocrity.springclean.application.Application;
import us.hypermediocrity.springclean.application.ApplicationImpl;
import us.hypermediocrity.springclean.domain.port.CurrencyExchangePort;
import us.hypermediocrity.springclean.domain.port.CustomerPort;
import us.hypermediocrity.springclean.domain.port.InvoicePort;
import us.hypermediocrity.springclean.domain.usecase.MakePayment;
import us.hypermediocrity.springclean.domain.usecase.MakePaymentUsecase;
import us.hypermediocrity.springclean.domain.usecase.ViewInvoice;
import us.hypermediocrity.springclean.domain.usecase.ViewInvoiceUsecase;

@SpringBootApplication
public class SpringCleanApplication {
  public static void main(String[] args) {
    SpringApplication.run(SpringCleanApplication.class, args);
  }

  @Autowired
  InvoicePort invoicePort;

  @Autowired
  CustomerPort customerPort;

  @Autowired
  CurrencyExchangePort exchangePort;

  @Autowired
  ViewInvoice viewInvoice;

  @Autowired
  MakePayment makePayment;

  // TODO I'd like to do this and still keep ApplicationImpl() package-private.
  // Alternatively, maybe move ApplicationImpl to some other package? Or use
  // Jigsaw modules?
  @Bean
  public Application getApplication() {
    return new ApplicationImpl(viewInvoice, makePayment, invoicePort, customerPort);
  }

  @Bean
  public MakePayment getMakePayment() {
    return new MakePaymentUsecase();
  }

  @Bean
  public ViewInvoice getViewInvoice() {
    return new ViewInvoiceUsecase(exchangePort);
  }

  @Bean
  public InvoicePort getInvoicePort() {
    return new InvoicePortAdapter();
  }

  @Bean
  public CustomerPort getCustomerPort() {
    return new CustomerPortAdapter();
  }

  @Bean
  public CurrencyExchangePort getCurrencyExchangePort() {
    return new CurrencyConverterAdapter();
  }
}
