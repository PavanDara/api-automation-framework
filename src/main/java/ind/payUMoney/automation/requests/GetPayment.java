package ind.payUMoney.automation.requests;


import ind.payUMoney.automation.utils.SessionVariableHolder;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;

public class GetPayment {
    public static String GET_PAYMENT_URL = "/payment/op/getPaymentResponse";

    public Response getPayment() {
//        RestAssured.proxy("proxy.xxx.com",xxxx);
        Response response=SerenityRest.given().
                header("Authorization", SessionVariableHolder.authorizationKey).
                formParam("merchantKey",SessionVariableHolder.merchantKey).
                formParam("merchantTransactionIds",SessionVariableHolder.merchantTransactionIds).
                post(GET_PAYMENT_URL);
        return response;
    }
}
