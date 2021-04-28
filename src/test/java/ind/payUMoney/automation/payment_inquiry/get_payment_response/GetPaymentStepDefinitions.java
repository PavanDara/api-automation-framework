package ind.payUMoney.automation.payment_inquiry.get_payment_response;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import ind.payUMoney.automation.requests.GetPayment;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Steps;
import org.junit.Assert;

public class GetPaymentStepDefinitions {

    @Steps
    GetPayment getpayment;

    private static Response get_address_response = null;


    @When("^I trigger getPaymentResponse API request with valid authorization Id")
    public void getPaymentRequestCall() throws Throwable {
        get_address_response = getpayment.getPayment();
    }

    @Then("^I can see (.*) Ok in the response$")
    public void responseReceivedWithStatusCodeForGetPayment(long statusCode) {
        Assert.assertEquals(statusCode, get_address_response.getStatusCode());
    }



}
