package ind.payUMoney.automation.requests;


import ind.payUMoney.automation.data_mappers.AccountMapper;
import ind.payUMoney.automation.utils.ContentTypes;
import ind.payUMoney.automation.utils.SessionVariableHolder;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Steps;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Set;

public class CreateInvoiceV2 {
    public static String CREATE_INVOICE_URL = "/invoicing/v2";

    @Steps
    AccountMapper accountMapper;

    public Response createInvoice() {
//        RestAssured.proxy("proxy.xxx.com",xxxx);
        Response response=SerenityRest.given().contentType(ContentTypes.APPLICATION_JSON).
                header("Authorization", "Bearer " + SessionVariableHolder.authentication_token).
                header("merchantId",accountMapper.getAccountData("username")).
                body(postCreateInvoiceRequestBody()).
                when().redirects().follow(false).log().all().
                post(CREATE_INVOICE_URL);
        return response;
    }

    public String postCreateInvoiceRequestBody()  {
//        String currentDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.'Z'").format(Calendar.getInstance().getTime());
//        String currDate = DateTimeFormatter.ISO_DATE_TIME.format(LocalDateTime.now());
//        String currDate = new Date().toISOString();
        LinkedHashMap request_body = new LinkedHashMap();
        JSONObject amount = new JSONObject();
        JSONObject channels = new JSONObject();
        JSONObject customParams = new JSONObject();
        JSONObject address = new JSONObject();

        amount.put("subAmount", "200.005");
        channels.put("viaEmail", true);
        channels.put("viaSms", true);
        customParams.put("property1", "string");
        customParams.put("property2", "string");
        address.put("addressCity", accountMapper.getAccountData("addressCity"));
        address.put("addressPin", accountMapper.getAccountData("addressPin"));
        address.put("addressState", accountMapper.getAccountData("addressState"));
        address.put("addressStreet1", accountMapper.getAccountData("addressStreet1"));
        address.put("addressStreet2", accountMapper.getAccountData("addressStreet2"));

        request_body.put("id", "INV"+ RandomStringUtils.randomNumeric(4));
        request_body.put("adjustment",accountMapper.getAccountData("adjustment") );
        request_body.put("amount", accountMapper.getAccountData("subAmount"));
        request_body.put("channels", channels);
        request_body.put("createdDate", DateTimeFormatter.ISO_DATE_TIME.format(LocalDateTime.now()));
        request_body.put("currency", "INR");
        request_body.put("customParams", customParams);
        request_body.put("address", address);
        request_body.put("email", accountMapper.getAccountData("email"));
        request_body.put("mobile", accountMapper.getAccountData("username"));
        request_body.put("name", accountMapper.getAccountData("username"));
        request_body.put("description", accountMapper.getAccountData("description"));
        request_body.put("expiryDate", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:s").format(Calendar.getInstance().getTime()));
        request_body.put("furl", accountMapper.getAccountData("furl"));
        request_body.put("maxPaymentsAllowed", accountMapper.getAccountData("maxPaymentsAllowed"));
        request_body.put("merchantId", accountMapper.getAccountData("username"));
        request_body.put("notes", accountMapper.getAccountData("description"));
        request_body.put("surl", accountMapper.getAccountData("surl"));
        request_body.put("isPartialPaymentAllowed", false);
        request_body.put("minAmountForCustomer", accountMapper.getAccountData("minAmountForCustomer"));

        return postCreateInvoicePatchObject(request_body);
    }

    private String postCreateInvoicePatchObject(LinkedHashMap request) {
        JSONObject patch_object = new JSONObject();
        Set<String> keys = request.keySet();
        for(String key:keys){
            patch_object.put(key,request.get(key));
        }
        return patch_object.toString();
    }
}
