package ind.payUMoney.automation.shared;

import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import ind.payUMoney.automation.data_mappers.AccountMapper;
import ind.payUMoney.automation.utils.DataFactory;
import io.restassured.RestAssured;
import io.restassured.specification.ProxySpecification;
import ind.payUMoney.automation.utils.*;
import net.thucydides.core.annotations.Steps;
import java.util.Collection;


public class GenericStepDefinitions {

    public static Collection<String> tagNames;
    private static String currentEndpoint = null;
    private static Boolean hasTheEndpointChanged = false;
    private final String file_path="\\src\\test\\resources\\data\\test.xlsx";

    @Steps
    AuthenticationManager authenticationManager;
    @Steps
    AccountMapper accountMapper;

    @Before
    public void setupRealData(Scenario scenario) throws Exception {
        tagNames = scenario.getSourceTagNames();
        setSessionVariables();
        EnvironmentVariables.setAPIEnvironmentBaseURL();
        DataFactory.parseDataFile(SessionVariableHolder.node_under_test, SessionVariableHolder.end_point_under_test);
    }

    private void setSessionVariables() {
        SessionVariableHolder.account_under_test="";
        SessionVariableHolder.environment_under_test = System.getProperty("env");
        getEndPointName();
        getNodeName();
    }

    private void getEndPointName() {
        for (String tagName : tagNames) {
            if (tagName.contains("endpoint")) {
                SessionVariableHolder.end_point_under_test = tagName.substring(tagName.indexOf("=") + 1);
            }
        }
    }

    private void getNodeName() {
        for (String tagName : tagNames) {
            if (tagName.contains("node")) {
                SessionVariableHolder.node_under_test = tagName.substring(tagName.indexOf("=") + 1);
            }
        }
    }

    @Given("^merchantKey is valid and merchantTransactionId is valid")
    public void dataSetupGetPaymentCall() throws Throwable {
        SessionVariableHolder.merchantKey = "40747T";
        SessionVariableHolder.merchantTransactionIds = "396132-58876806";
        SessionVariableHolder.authorizationKey = "KpNTiy57L6OFjS2D3TqPod8+6nfGmRVwVMi5t9jR4NU=";
    }

    @Given("^Access token generated from token API with scope '(.*)'")
    public void oauthTokenGeneration(String scope) throws Throwable {
        //reset request specifications to start each test clean
        RestAssured.requestSpecification = null;
        String username = "";
        String password = "";
        SessionVariableHolder.oauth_scope = scope;
        username = accountMapper.getAccountData("username");
        password = accountMapper.getAccountData("password");
        authenticationManager.setOAuthToken(username, password);
    }
}
