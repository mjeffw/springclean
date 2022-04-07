package us.hypermediocrity.springclean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import us.hypermediocrity.springclean.adapter.bankaccount.BankAccountAdapter;
import us.hypermediocrity.springclean.adapter.creditcard.CreditCardAdapter;
import us.hypermediocrity.springclean.adapter.currencyexchange.CurrencyConverterAdapter;
import us.hypermediocrity.springclean.adapter.customer.CustomersAdapter;
import us.hypermediocrity.springclean.adapter.invoice.InvoicesAdapter;
import us.hypermediocrity.springclean.adapter.paypal.PayPalAdapter;
import us.hypermediocrity.springclean.domain.port.CurrencyExchangeService;
import us.hypermediocrity.springclean.domain.port.Customers;
import us.hypermediocrity.springclean.domain.port.Invoices;
import us.hypermediocrity.springclean.domain.port.MoneyTransferPortRouter;
import us.hypermediocrity.springclean.domain.port.PaymentService;
import us.hypermediocrity.springclean.domain.usecase.MakePayment;
import us.hypermediocrity.springclean.domain.usecase.MakePaymentUsecase;
import us.hypermediocrity.springclean.domain.usecase.ViewInvoice;

@SpringBootApplication
public class SpringCleanApplication {
  public static void main(String[] args) {
    SpringApplication.run(SpringCleanApplication.class, args);
  }

  @Autowired Invoices invoices;
  @Autowired Customers customers;
  @Autowired CurrencyExchangeService exchangeService;
  @Autowired PaymentService paymentService;
  @Autowired PaymentService payPalPaymentService;
  @Autowired PaymentService bankAccountPaymentService;
  @Autowired PaymentService creditCardPort;
  @Autowired ViewInvoice viewInvoice;
  @Autowired MakePayment makePayment;

  @Bean
  public Usecases getUsecases() {
    return new Usecases(viewInvoice, makePayment);
  }

  @Bean
  public MakePayment getMakePayment() {
    return new MakePaymentUsecase(invoices, exchangeService, bankAccountPaymentService);
  }

  @Bean
  public Invoices getInvoicePort() {
    return new InvoicesAdapter();
  }

  @Bean
  public Customers getCustomerPort() {
    return new CustomersAdapter();
  }

  @Bean
  public CurrencyExchangeService getCurrencyExchangePort() {
    return new CurrencyConverterAdapter();
  }

  @Bean
  public PaymentService getMoneyTransferPort() {
    return new MoneyTransferPortRouter(payPalPaymentService, bankAccountPaymentService, creditCardPort);
  }

  @Bean
  public PaymentService getPayPalPort() {
    return new PayPalAdapter();
  }

  @Bean
  public PaymentService getBankAccountPort() {
    return new BankAccountAdapter();
  }

  @Bean
  public PaymentService creditCardPort() {
    return new CreditCardAdapter();
  }
}
