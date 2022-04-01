package us.hypermediocrity.springclean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import us.hypermediocrity.springclean.adapter.currencyconvert.CurrencyConverterAdapter;
import us.hypermediocrity.springclean.adapter.customer.CustomerPortAdapter;
import us.hypermediocrity.springclean.adapter.invoice.InvoicePortAdapter;
import us.hypermediocrity.springclean.domain.Application;
import us.hypermediocrity.springclean.domain.ApplicationImpl;
import us.hypermediocrity.springclean.domain.port.CurrencyExchangePort;
import us.hypermediocrity.springclean.domain.port.CustomerPort;
import us.hypermediocrity.springclean.domain.port.InvoicePort;

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

  // TODO I'd like to do this and still keep ApplicationImpl() package-private.
  // Alternatively, maybe move ApplicationImpl to some other package? Or use
  // Jigsaw modules?
  @Bean
  public Application getApplication() {
    return new ApplicationImpl(exchangePort, invoicePort, customerPort);
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
