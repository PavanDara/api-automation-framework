#### Automation of PayUMoney APIs

**Pre-requisites :**  
Java Run Time Environment installed on the system

**Frame work details :**  
Framework used : Serenity (http://thucydides.info/docs/serenity-staging/)  
Serenity BDD is an open source library that aims to make the idea of living documentation a reality. Serenity BDD helps you write cleaner and more maintainable automated acceptance and regression tests faster. Serenity also uses the test results to produce illustrated, narrative reports that document and describe what your application does and how it works. Serenity tells you not only what tests have been executed, but more importantly, what requirements have been tested.

Build Tool Used : Gradle (https://docs.gradle.org/current/userguide/userguide.html)

Note : For gradle we are including the wrapper as part of the project, so no need to install it separately.

**Clone Project :**

**Writing BDD Features**  
Gherkin files (src/test/resources/features/chk_Merchant_Txn_Status.feature)
Feature runner with cucumber config (src/test/java ind.payUMoney.automation.behavepro.CucumberTestSuite.java)
Package that implement the BDD steps (src/test/java ind.payUMoney.automation.payment_inquiry.chk_merchant_txn_status)
Most features will also target StepDefinitions shared among all features (src/test/java ind.payUMoney.automation.shared)

Example :

```

@CucumberOptions(
        plugin = {"pretty"},
        /*
         * feature file or directory
        */
        features = "src/test/resources/features/chk_Merchant_Txn_Status.feature",
        /*
         * packages that contain step definitions
        */
        glue = { "ind.payUMoney.automation.payment_inquiry.chk_merchant_txn_status", "ind.payUMoney.automation.shared"}
)
```

**How to Run the Project**

env - Environment to run (Can be mock,tst,acc)
Optional : -Dcucumber.options = "--tags {{tags to run}}" : Please look down the page for how to use this

```
gradlew clean test aggregate -Denv={{env}}
```

Mac OSX :

```
gradle clean test aggregate -Denv={{env}}
```

Linux :

```
./gradlew clean test aggregate -Denv={{env}}
```

**Data Management**

The DataFactory is activated by the following flag:

```bash
-Denv=tst
```

First we need a json file that will contain a real value for each data value used in BDD.
The mock values in this example are "invoice_id" and "merchant_id".

src/test/resources/data/tst.json

```json
{
  "accounts": {
    "default-user": {
        "invoice_id": "INVxxxx",
        "merchant_id": "MERxxxx"
    }
  }
}
```

All data used in the BDD scenario's should pass through a DataFactory implementation. By default
this DataFactory will return the passed mock value.

```java
DataFactory.get("invoice_id", "accounts.default-user");
// or
DataFactory.get("invoice_id", "accounts", "default-user");

// Both return "invoice_id"
```

To decouple the BDD implementation from the json structure a custom DataMapper should be created.

src/main/java/ ind.payUMoney.automation.data_mappers.AccountMapper

```java
public String getAccountData(String dataKey) {
        return (String) DataFactory.get(dataKey, "accounts", SessionVariableHolder.accountUnderTest, dataKey);
    }
// Inside a BDD test:
accountMapper.getAccountData("invoice_id");
// Returns : "invoice_id" (based on flags env and user to test)
```

**Running only given tags**

@node and @endpoint = these will support the test data file path.

Exp: user want to access data\payment_inquiry\chk_merchant_txn_status\chk_merchant_txn_status_tst.json file.

in this case node will be payment_inquiry and endpoint will be chk_merchant_txn_status.

To run only a particular feature or scenario and ignore others

    1.Add the tag to the feature or scenario in a feature
        Ex :
        @test-run
          Scenario: Get update status of the transaction(s) with PayUmoney.
			Given merchantKey is valid and merchantTransactionId is valid
			When I trigger chkMerchantTxnStatus API request with valid authorization Id
			Then I can see 200 Ok in the chkMerchantTxnStatus response
			And chkMerchantTxnStatus response contains valid amount, paymentId and transaction status
	
    2. Run the test by providing the feature tag
        Ex : gradle clean test aggregate -Denv={{env}} -Dcucumber.options="--tags @feature=login" (Mac or Windows)
    More Information can be found here : https://johnfergusonsmart.com/running-serenity-bdd-tests-with-tags/


Ex: gradlew clean test aggregate -Denv="test" -Dcucumber.options="--tags @test-run"