package ind.payUMoney.automation.payment_inquiry.chk_merchant_txn_status;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import ind.payUMoney.automation.requests.ChkMerchantTxnStatus;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Steps;
import org.junit.Assert;

public class ChkMerchantTxnStatusStepDefinitions {

    @Steps
    ChkMerchantTxnStatus chkMerchantTxnStatus;

    private static Response chk_merchant_txn_status_response = null;


    @When("^I trigger chkMerchantTxnStatus API request with valid authorization Id")
    public void chkMerchantTxnStatusRequestCall() throws Throwable {
        chk_merchant_txn_status_response = chkMerchantTxnStatus.chkMerchantTxnStatus();
    }

    @Then("^I can see (.*) Ok in the chkMerchantTxnStatus response$")
    public void responseReceivedWithStatusCodeForChkMerchantTxnStatus(long statusCode) {
        Assert.assertEquals(statusCode, chk_merchant_txn_status_response.getStatusCode());
    }

    @And("^chkMerchantTxnStatus response contains valid amount, paymentId and transaction status$")
    public void verifyChkMerchantTxnStatusResponse() {
        Assert.assertNotNull(chk_merchant_txn_status_response.then().extract().path("invoices.dunning_level"));
//        Assert.AssertNotNull(chk_merchant_txn_status_response.then().extract().path("result.amount").toString());
    }



}
