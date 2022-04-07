package us.hypermediocrity.springclean.domain.usecase;

import java.time.LocalDate;
import java.util.Currency;

import us.hypermediocrity.springclean.common.Money;
import us.hypermediocrity.springclean.domain.entity.Customer;
import us.hypermediocrity.springclean.domain.entity.Invoice;
import us.hypermediocrity.springclean.domain.entity.LineItem;

public class TestFixtures {
  public static Customer customer() {
    var customer = new Customer("customer");
    customer.accountNumber("account");
    customer.name("Acme Corp");
    customer.currency(Currency.getInstance("USD"));
    return customer;
  }

  public static Invoice invoiceSimple() {
    var simpleInvoice = new Invoice("simple");
    simpleInvoice.date(LocalDate.of(2022, 3, 31));
    simpleInvoice.customerId("customer");
    simpleInvoice.customer(TestFixtures.customer());
    simpleInvoice.amountPaid(Money.ZERO);
    return simpleInvoice;
  }

  public static Invoice invoiceMultiItem() {
    var multipleItemInvoice = new Invoice("multi");
    multipleItemInvoice.date(LocalDate.of(2022, 1, 1));
    multipleItemInvoice.customerId("customer");
    multipleItemInvoice.customer(TestFixtures.customer());
    multipleItemInvoice.addLineItem(new LineItem("product-a", 4, new Money(39.95, Currency.getInstance("USD")))); // $159.80
    multipleItemInvoice.addLineItem(new LineItem("product-b", 1, new Money(2, Currency.getInstance("USD")))); // $2.00
    multipleItemInvoice.addLineItem(new LineItem("product-c", 2, new Money(17.50, Currency.getInstance("USD")))); // $35.00
    multipleItemInvoice.amountPaid(Money.ZERO);
    return multipleItemInvoice;
  }

}
