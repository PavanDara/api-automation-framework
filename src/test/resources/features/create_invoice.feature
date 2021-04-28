@node=invoicing @endpoint=create_invoice_v2
Feature: PayUMoney APIs to create, search and manage a payment link and email/SMS Invoice.

  @test-run
  Scenario: Create invoice and send it to the customer.
    Given Access token generated from token API with scope 'create_payumoney_invoice'
    When I trigger create invoicing API request with valid authorization, merchantId and invoice details
    Then I can see 200 Ok in the create invoice v2 api response
#    And chkMerchantTxnStatus response contains valid invoiceId,