package ind.payUMoney.automation.request;


import io.restassured.RestAssured;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;

public class GetPayment {
    public static String GET_PAYMENT_URL = "/payment/op/getPaymentResponse";

    public Response getPayment(String merchantKey, String merchantTransactionIds, String authorizationKey) {
        RestAssured.baseURI="https://test.payumoney.com";
//        RestAssured.proxy("proxy.xxx.com",xxxx);
        Response response=SerenityRest.given().
                header("Authorization",authorizationKey).
                formParam("merchantKey",merchantKey).
                formParam("merchantTransactionIds",merchantTransactionIds).
                post(GET_PAYMENT_URL);
        return response;
    }
}
