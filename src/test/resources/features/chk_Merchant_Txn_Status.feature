@node=payment_inquiry @endpoint=chk_merchant_txn_status
Feature: PayUmoney APIs to enquire regarding a transaction done through Payumoney.

  @Completed
  Scenario: Get update status of the transaction(s) with PayUmoney.
    Given merchantKey is valid and merchantTransactionId is valid
    When I trigger chkMerchantTxnStatus API request with valid authorization Id
    Then I can see 200 Ok in the chkMerchantTxnStatus response
    And chkMerchantTxnStatus response contains valid amount, paymentId and transaction status