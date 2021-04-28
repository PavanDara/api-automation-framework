package ind.payUMoney.automation.getPayment;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import ind.payUMoney.automation.request.GetPayment;
import ind.payUMoney.automation.utils.ExcelReader;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Steps;
import org.junit.Assert;

public class GetPaymentStepDefinitions {

    @Steps
    GetPayment getpayment;

    @Steps
    ExcelReader excelReader;

    private static Response get_address_response = null;
    private String merchantKey;
    private String authorizationKey;
    private String merchantTransactionIds;
    private final String file_path="\\src\\test\\resources\\data\\test.xlsx";



    @Given("^merchantKey is valid and merchantTransactionId is valid")
    public void dataSetupGetPaymentCall() throws Throwable {
        merchantKey =excelReader.readTestData(System.getProperty("user.dir")+file_path,0);
        merchantTransactionIds = excelReader.readTestData(System.getProperty("user.dir")+file_path,1);
        authorizationKey = excelReader.readTestData(System.getProperty("user.dir")+file_path,2);
    }

    @When("^I trigger getPaymentResponse API request with valid authorization Id")
    public void getPaymentRequestCall() throws Throwable {
        get_address_response = getpayment.getPayment(merchantKey, merchantTransactionIds, authorizationKey);
    }

    @Then("^I can see (.*) Ok in the response$")
    public void responseReceivedWithStatusCodeForGetAddressDetails(long statusCode) {
        Assert.assertEquals(statusCode, get_address_response.getStatusCode());
    }



}
