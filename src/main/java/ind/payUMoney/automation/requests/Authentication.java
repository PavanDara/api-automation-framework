package ind.payUMoney.automation.requests;

import ind.payUMoney.automation.utils.ContentTypes;
import ind.payUMoney.automation.utils.SessionVariableHolder;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.pages.PageObject;
import org.json.simple.*;

import java.util.LinkedHashMap;
import java.util.Set;

public class Authentication extends PageObject {
    private final String oAuthClientID = "fdfsdh324hgjdfhgf3";
    private final String oAuthClientSecret = "asjvkjfdbavd";
    private final String oAuthBaseURL = "https://uat-accounts.payu.in";
    private final String oAuthGrantType = "password";
    private final String oAuthURL = "/oauth/token";

    public Response getOAuthToken(String username, String password) {
        String storeBaseURI = RestAssured.baseURI;
        RestAssured.baseURI = oAuthBaseURL;

        LinkedHashMap request_body = new LinkedHashMap();
        JSONObject payload = new JSONObject();
        payload.put("client_id",oAuthClientID );
        if(oAuthGrantType.equalsIgnoreCase("client_credentials"))
            payload.put("client_secret",oAuthClientSecret );
        payload.put("grant_type",oAuthGrantType );
        payload.put("scope",SessionVariableHolder.oauth_scope );
        payload.put("username",username );
        payload.put("password",password );
        request_body.put("payload",payload);

        Response authenticationResponse = SerenityRest.given().contentType(ContentTypes.APPLICATION_JSON).
                body(postOAuthTokenPatchObject(request_body)).
//                formParam("client_id", oAuthClientID).
//                formParam("client_secret", oAuthClientSecret).
//                formParam("grant_type", oAuthGrantType).
//                formParam("scope", SessionVariableHolder.oauth_scope).
//                formParam("username", username).
//                formParam("password", password).
                when().redirects().follow(false).log().all().
                post(oAuthURL);
        RestAssured.baseURI = storeBaseURI;
        int response = authenticationResponse.getStatusCode();
        return authenticationResponse;
    }

    private String postOAuthTokenPatchObject(LinkedHashMap request) {
        JSONObject patch_object = new JSONObject();
        Set<String> keys = request.keySet();
        for(String key:keys){
            patch_object.put(key,request.get(key));
        }
        return patch_object.toString();
    }
}
