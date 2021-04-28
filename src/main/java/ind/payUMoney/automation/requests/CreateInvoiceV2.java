package ind.payUMoney.automation.requests;


import ind.payUMoney.automation.data_mappers.AccountMapper;
import ind.payUMoney.automation.utils.ContentTypes;
import ind.payUMoney.automation.utils.SessionVariableHolder;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Steps;
import java.util.LinkedHashMap;
import java.util.Set;

public class CreateInvoiceV2 {
    public static String CREATE_INVOICE_URL = "/invoicing/v2";

    @Steps
    AccountMapper accountMapper;

    public Response createInvoice() {
        RestAssured.proxy("proxy.rwe.com",8080);
        Response response=SerenityRest.given().contentType(ContentTypes.APPLICATION_JSON).
                header("Authorization", "Bearer " + SessionVariableHolder.authentication_token).
                header("merchantId",accountMapper.getAccountData("username")).
                post(CREATE_INVOICE_URL);
        return response;
    }

    public Response putMarketDataConsent(LinkedHashMap request) {
        return SerenityRest.given().contentType(ContentType.JSON).body(putMarketDataConsentPatchObject(request))
                .put(putMarketDataConsentUrl());
    }

    private String putMarketDataConsentPatchObject(LinkedHashMap request) {
        JSONObject patch_object = new JSONObject();
        Set<String> keys = request.keySet();
        for(String key:keys){
            patch_object.put(key,request.get(key));
        }
        return patch_object.toString();
    }
}
