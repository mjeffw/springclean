@Ignore
Feature: View Invoice
  Scenario: Unknown invoice number
    Given invoice number "1234" does not exist
    When the customer asks to view that invoice
    Then they should be told the invoice was not found