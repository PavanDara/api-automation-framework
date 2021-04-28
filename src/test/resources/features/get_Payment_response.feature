@node=payment_inquiry @endpoint=get_payment_response
Feature: Payumoney APIs to enquire regarding a transaction done through Payumoney.

  Scenario: Get Payment Response
    Given merchantKey is valid and merchantTransactionId is valid
    When I trigger getPaymentResponse API request with valid authorization Id
    Then I can see 200 Ok in the response