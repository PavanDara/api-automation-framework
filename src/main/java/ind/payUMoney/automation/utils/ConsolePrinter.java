package ind.payUMoney.automation.utils;

import cucumber.api.java.en.Then;
import net.thucydides.core.annotations.Step;

public class ConsolePrinter {
    public static void PrintToConsole(String textToPrint) {
        System.out.println("==================== Debug =================");
        System.out.println("==================== Debug =================");
        System.out.println();
        System.out.println(textToPrint);
        System.out.println();
        System.out.println("==================== Debug =================");
        System.out.println("==================== Debug =================");
    }

    public static void main(String[] args) {
    }

    @Step
    @Then("^Step : (.*)$")
    public void subStep(String stepDescription) {
        return;
    }
}
