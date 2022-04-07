package us.hypermediocrity.springclean.domain.usecase;

import static us.hypermediocrity.springclean.common.Money.USD;

import java.util.Currency;

import us.hypermediocrity.springclean.common.Money;
import us.hypermediocrity.springclean.domain.common.DomainException;
import us.hypermediocrity.springclean.domain.common.InvoiceNotFoundException;
import us.hypermediocrity.springclean.domain.common.PaymentConfirmation;
import us.hypermediocrity.springclean.domain.common.PaymentInfo;
import us.hypermediocrity.springclean.domain.common.PaymentReason;
import us.hypermediocrity.springclean.domain.entity.Invoice;
import us.hypermediocrity.springclean.domain.port.CurrencyExchangeService;
import us.hypermediocrity.springclean.domain.port.InvoiceEntityGateway;
import us.hypermediocrity.springclean.domain.port.PaymentService;
import us.hypermediocrity.springclean.domain.port.PaymentStatus;

public class MakePaymentUsecase implements MakePayment {
  private final InvoiceEntityGateway invoices;
  private final CurrencyExchangeService exchangeService;
  private final PaymentService paymentService;

  public MakePaymentUsecase(InvoiceEntityGateway invoices, CurrencyExchangeService exchangeService, PaymentService paymentService) {
    this.invoices = invoices;
    this.exchangeService = exchangeService;
    this.paymentService = paymentService;
  }

  @Override
  public PaymentConfirmation execute(String invoiceId, Money paymentAmount, PaymentInfo paymentInfo)
      throws DomainException {
    // Look up the invoice
    Invoice invoice = invoices.getInvoice(invoiceId).orElseThrow(() -> new InvoiceNotFoundException(invoiceId));

    // If nothing is due, immediately return with "Amount Due is Zero".
    if (invoice.isNothingDue()) {
      return new PaymentConfirmation(Money.ZERO, PaymentReason.AMOUNT_DUE_IS_ZERO, Money.ZERO);
    }

    // Adjust the payment amount to the common currency (USD).
    var localCurrency = paymentAmount.currency;
    var exchangeRate = 1.0;

    if (localCurrency != Money.USD) {
      exchangeRate = exchangeService.getCurrentExchangeRate(localCurrency, USD);
    }

    var adjustedPayment = convertLocalCurrencyToUSD(paymentAmount, exchangeRate);

    // Request payment/funds transfer.
    var response = paymentService.request(adjustedPayment, paymentInfo);

    // If payment is successful...
    if (response.status() == PaymentStatus.SUCCESS) {
      // ...add the payment to the invoice
      var amountPaid = invoice.pay(adjustedPayment);

      // ...update the Invoice system
      invoices.update(invoice);

      var amountPaidInLocalCurrency = convertUSDToLocalCurrency(localCurrency, exchangeRate, amountPaid);

      // ...inform the customer of the payment result and any remaining balance.
      if (invoice.amountDue().greaterThan(Money.ZERO)) {
        return new PaymentConfirmation(amountPaidInLocalCurrency, PaymentReason.PARTIAL_PAYMENT,
            convertUSDToLocalCurrency(localCurrency, exchangeRate, invoice.amountDue()));
      }

      // If overpaid, set the "balance" to the refund amount.
      var refund = amountPaid.minus(adjustedPayment);
      return new PaymentConfirmation(amountPaidInLocalCurrency, PaymentReason.PAID_IN_FULL,
          convertUSDToLocalCurrency(localCurrency, exchangeRate, refund));
    }

    // If payment is failed, return failure and current amount due.
    return new PaymentConfirmation(Money.ZERO, PaymentReason.PROCESSING_FAILED, invoice.amountDue());
  }

  public Money convertUSDToLocalCurrency(Currency localCurrency, double exchangeRate, Money amountPaid) {
    return new Money(amountPaid.value / exchangeRate, localCurrency);
  }

  public Money convertLocalCurrencyToUSD(Money paymentAmount, double exchangeRate) {
    return new Money(paymentAmount.value * exchangeRate, USD);
  }
}
