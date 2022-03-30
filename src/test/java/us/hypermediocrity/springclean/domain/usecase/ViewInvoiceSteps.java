package us.hypermediocrity.springclean.domain.usecase;

import org.springframework.beans.factory.annotation.Autowired;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import us.hypermediocrity.springclean.domain.usecase.exceptions.DomainException;

public class ViewInvoiceSteps {
  private String invoiceNumber;

  @Autowired
  private ViewInvoice usecase;

  @Given("invoice number {string} does not exist")
  public void invoiceNumberDoesNotExist(String int1) {
    this.invoiceNumber = int1;
  }

  @When("the customer asks to view that invoice")
  public void theCustomerAsksToViewThatInvoice() throws DomainException {
    // usecase.execute(this.invoiceNumber);
  }

  @Then("they should be told the invoice was not found")
  public void theyShouldBeToldTheInvoiceWasNotFound() {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }
}
