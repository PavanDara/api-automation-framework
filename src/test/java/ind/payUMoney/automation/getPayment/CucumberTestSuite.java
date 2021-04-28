package ind.payUMoney.automation.getPayment;

import cucumber.api.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        plugin = {"pretty"},
        features = "src/test/resources/features/get_Payment_response.feature",
        glue = { "ind.payUMoney.automation.getPayment"}
)

public class CucumberTestSuite {
}

