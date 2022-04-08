package us.hypermediocrity.springclean;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import us.hypermediocrity.springclean.adapter.bankaccount.BankAccountAdapter;
import us.hypermediocrity.springclean.adapter.creditcard.CreditCardAdapter;
import us.hypermediocrity.springclean.adapter.currencyexchange.CurrencyConverterAdapter;
import us.hypermediocrity.springclean.adapter.customer.CustomersAdapter;
import us.hypermediocrity.springclean.adapter.invoice.InvoicesAdapter;
import us.hypermediocrity.springclean.adapter.paymentrouter.MoneyTransferPortRouter;
import us.hypermediocrity.springclean.adapter.paypal.PayPalAdapter;
import us.hypermediocrity.springclean.domain.port.CurrencyExchangeService;
import us.hypermediocrity.springclean.domain.port.Customers;
import us.hypermediocrity.springclean.domain.port.Invoices;
import us.hypermediocrity.springclean.domain.port.PaymentService;
import us.hypermediocrity.springclean.domain.usecase.MakePayment;
import us.hypermediocrity.springclean.domain.usecase.MakePaymentUsecase;
import us.hypermediocrity.springclean.domain.usecase.ViewInvoice;
import us.hypermediocrity.springclean.domain.usecase.ViewInvoiceUsecase;

@SpringBootApplication
public class SpringCleanApplication {
  public static void main(String[] args) {
    SpringApplication.run(SpringCleanApplication.class, args);
  }

  @Bean
  public Usecases usecases() {
    return new Usecases(viewInvoice(), makePayment());
  }

  public MakePayment makePayment() {
    return new MakePaymentUsecase(invoices(), currencyExchange(), router());
  }

  public ViewInvoice viewInvoice() {
    return new ViewInvoiceUsecase(invoices(), customers(), currencyExchange());
  }

  @Bean
  public Invoices invoices() {
    return new InvoicesAdapter();
  }

  @Bean
  public Customers customers() {
    return new CustomersAdapter();
  }

  @Bean
  public CurrencyExchangeService currencyExchange() {
    return new CurrencyConverterAdapter();
  }

  @Bean
  public PaymentService router() {
    return new MoneyTransferPortRouter(paypal(), bank(), card());
  }

  public PaymentService card() {
    return new CreditCardAdapter();
  }

  public PaymentService paypal() {
    return new PayPalAdapter();
  }

  public PaymentService bank() {
    return new BankAccountAdapter();
  }
}
