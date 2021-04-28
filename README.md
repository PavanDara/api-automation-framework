# Essent test automation seed
## Forked from

Git:

    git clone https://github.com/serenity-bdd/serenity-cucumber-starter.git
    cd serenity-cucumber-starter
[Base docs](https://github.com/serenity-bdd/serenity-cucumber-starter)

## Run project
```
./gradlew clean test aggregate --rerun-tasks (optionals: -DtestData=test.json)
```

## Intro
Serenity is an all in one solution for creating BDD tests. This repository 
is a seed project for implementing BDD without any additional platform specific
implementations.

Seeds for Web and App can be found here:
```
/web
/app
```

## Content
### Seed
All BDD test code (step definitions, runners) should be put in src/test/java and all helper code should be put in src/main/java.

#### Feature (for example coffee)
- Gherkin files (src/test/resources/features/order_a_coffee.feature)
- Feature runner with cucumber config (src/test/java nl.essent.automation.coffee.CucumberTestSuite)
- Step definitions that implement the BDD steps (src/test/java nl.essent.automation.coffee.StepDefinitions)
- Most features will also target StepDefinitions shared among all features (src/test/java nl.essent.automation.shared)
```

@CucumberOptions(
        plugin = {"pretty"},
        /*
         * feature file or directory
        */
        features = "src/test/resources/features/order_a_coffee.feature",
        /*
         * packages that contain step definitions
        */
        glue = { "nl.essent.automation.coffee", "nl.essent.automation.shared"}
)
```
#### MockServer
This seed provides a WireMock runnable in ./mock-server.

Endpoint configurations can be found in the mappings directory and response bodies can be found in __files.
The response bodies are referenced by the configurations of the mappings.

```bash
cd mock-server
./run-mock-server.sh
``` 

Runs by default on localhost:7777

#### MockData
Mock data corresponds with the WireMock configuration. Sometimes you want to run the BDD tests with a real backend. This seed contains a mechanism to map mock data to real data.

Here follows an example implementation:

First we need a json file that will contain a real value for each mocked value used in BDD.
The mock values in this example are "coffee" and "tea". 

src/test/resources/data/test.json
```json
{
  "foodItems": {
    "drinks": {
      "coffee": "Current coffee brand",
      "tea": "Current tea brand"
    }
  }
}
```


All data used in the BDD scenario's that are mocked should pass through a DataFactory implementation. By default
this DataFactory will return the passed mock value. 

```java
DataFactory.get("coffee", "foodItems.drinks.coffee");
// or
DataFactory.get("coffee", "foodItems", "drinks", "coffee");

// Both return "coffee"
```

The DataFactory can be activated by passing the following flag:
```bash
-DtestData=test.json
```

If the testData flag is set, the output will change:
```java
DataFactory.get("coffee", "foodItems.drinks.coffee");
// or
DataFactory.get("coffee", "foodItems", "drinks", "coffee");

// Both return "Current coffee brand"
```

To decouple the BDD implementation from the json structure a custom DataMapper should be created.

src/main/java/ FoodItemsMapper
```java
public String getDrink(String drink) {
    return (String) DataFactory.get(drink, "foodItems", "drinks", drink);
}
// Inside a BDD test:
getDrink("coffee");
// Returns either: "coffee" or "Current coffee brand" (based on flag)
```
To run only a particular feature or scenario and ignore others
    1.Add the tag to the feature or scenario in a feature
        Ex : 
        @feature=coffee
        Feature: Order a coffee
    2. Run the test by providing the feature tag
        Ex : gradle test aggregate -Dcucumber.options="--tags @feature=coffee" (Mac or Windows)
            ./gradlew test aggregate -Dcucumber.options="--tags @feature=coffee" (Linux)
    More Information can be found here : https://johnfergusonsmart.com/running-serenity-bdd-tests-with-tags/
