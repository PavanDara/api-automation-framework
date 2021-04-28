package ind.payUMoney.automation.behavepro;

import cucumber.api.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        plugin = {"pretty"},
        features = "src/test/resources/features/",
        glue = { "ind.payUMoney.automation"}
)

public class CucumberTestSuite {
}

