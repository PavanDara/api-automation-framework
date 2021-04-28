package ind.payUMoney.automation.shared;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import ind.payUMoney.automation.utils.DataFactory;

public class GenericStepDefinitions {

    @Before
    public void setupRealData() {
        /*
            Setup the test data file if -DtestData is passed.
         */
        DataFactory.parseDataFile();
    }

    @Given("^I am thirsty$")
    public void i_am_thirsty() {

    }
}
