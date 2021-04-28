package ind.payUMoney.automation.requests;


import ind.payUMoney.automation.utils.SessionVariableHolder;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;

public class ChkMerchantTxnStatus {
    public static String CHK_MERCHANT_TXN_STATUS_URL = "/payment/payment/chkMerchantTxnStatus";

    public Response chkMerchantTxnStatus() {
        RestAssured.proxy("proxy.rwe.com",8080);
        Response response=SerenityRest.given().
                header("Authorization", SessionVariableHolder.authorizationKey).
                formParam("merchantKey",SessionVariableHolder.merchantKey).
                formParam("merchantTransactionIds",SessionVariableHolder.merchantTransactionIds).
                post(CHK_MERCHANT_TXN_STATUS_URL);
        return response;
    }
}
