package ind.payUMoney.automation.invoicing.create_invoice_v2;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import ind.payUMoney.automation.requests.ChkMerchantTxnStatus;
import ind.payUMoney.automation.requests.CreateInvoiceV2;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Steps;
import org.junit.Assert;

public class CreateInvoiceV2StepDefinitions {

    @Steps
    CreateInvoiceV2 createInvoice;

    private static Response create_invoice_response = null;


    @When("^I trigger create invoicing API request with valid authorization, merchantId and invoice details")
    public void createInvoiceRequestCall() throws Throwable {
        create_invoice_response = createInvoice.createInvoice();
    }

    @Then("^I can see (.*) Ok in the create invoice v2 api response$")
    public void responseReceivedWithStatusCodeForCreateInvoiceV2(long statusCode) {
        Assert.assertEquals(statusCode, create_invoice_response.getStatusCode());
    }

    @And("^Create invoice v2 api response contains valid amount, invoiceId and transaction status$")
    public void verifyCreateInvoiceV2Response() {
        Assert.assertNotNull(create_invoice_response.then().extract().path("result.id"));
        Assert.assertNotNull(create_invoice_response.then().extract().path("result.amount"));
        Assert.assertNotNull(create_invoice_response.then().extract().path("result.totalAmount"));
        Assert.assertNotNull(create_invoice_response.then().extract().path("result.status"));
        Assert.assertNotNull(create_invoice_response.then().extract().path("result.amountCollected"));
        Assert.assertNotNull(create_invoice_response.then().extract().path("result.amountDue"));
        Assert.assertNotNull(create_invoice_response.then().extract().path("status"));
    }



}
