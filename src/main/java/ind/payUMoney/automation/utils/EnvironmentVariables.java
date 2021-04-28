package ind.payUMoney.automation.utils;

import io.restassured.RestAssured;
import net.thucydides.core.annotations.Steps;
import ind.payUMoney.automation.data_mappers.EndPointMapper;

public class EnvironmentVariables {

    @Steps
    EndPointMapper endPointMapper;

    public static final String tst_environment = "test";
    public static final String acc_environment = "acc";
    public static final String dev_environment = "dev";


    public static void setAPIEnvironmentBaseURL() {
        String baseURI = "https://" + SessionVariableHolder.environment_under_test + ".payumoney.com";
        SessionVariableHolder.api_environment_base_url = baseURI;
        RestAssured.baseURI = baseURI;
    }
}
