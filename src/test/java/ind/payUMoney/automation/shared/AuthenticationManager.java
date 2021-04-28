package ind.payUMoney.automation.shared;

import io.restassured.response.Response;
import net.thucydides.core.annotations.Steps;
import ind.payUMoney.automation.requests.Authentication;
import ind.payUMoney.automation.utils.*;
import org.junit.Assert;
import java.io.UnsupportedEncodingException;


public class AuthenticationManager {

    @Steps
    Authentication authentication;

    public void setOAuthToken(String username, String password) {
        Response oauth_authentication_response = authentication.getOAuthToken(username, password);
        Assert.assertEquals(StatusCodes.REQUEST_OK_SUCCESS_CODE, oauth_authentication_response.getStatusCode());
        SessionVariableHolder.authentication_token = oauth_authentication_response.then().extract().path("access_token");
        ConsolePrinter.PrintToConsole("OAUTH TOKEN is:\n" + oauth_authentication_response.then().extract().path("access_token"));
    }

}