import com.sparta.ow.Calculator;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;

public class CalculatorStepDefs {

    private Calculator calculator;
    private List<Integer> numbers;
    private Integer actual;
    private ArithmeticException exception;

    @Given("I have a calculator")
    public void iHaveACalculator() {
        calculator = new Calculator();
        numbers = new ArrayList<>();
    }

    @And("I enter {int} and {int} into the calculator")
    public void iEnterAndIntoTheCalculator(int num1, int num2) {
        calculator.setNum1(num1);
        calculator.setNum2(num2);
    }

    @When("I press add")
    public void iPressAdd() {
        actual = calculator.add();
    }

    @Then("the result should be should be {int}")
    public void theResultShouldBeShouldBe(int expected) {
        Assertions.assertEquals(expected, actual);
    }

    @When("I press subtract")
    public void iPressSubtract() {
        actual = calculator.subtract();
    }

    @Then("the result should be <result>")
    public void theResultShouldBeResult(int expected) {
        Assertions.assertEquals(expected,actual);
    }

    @When("I press multiply")
    public void iPressMultiply() {
        actual = calculator.multiply();
    }

    @When("I press divide")
    public void iPressDivide() {
        try {
            actual = calculator.divide();
        } catch (ArithmeticException e) {
            exception = e;
        }
    }

    @Then("a DivideByZeroException should be thrown")
    public void aDivideByZeroExceptionShouldBeThrown() {
        Assertions.assertNotNull(exception, "Expected a DivideByZeroException to be thrown");
    }

    @Then("the exception should have the message {string}")
    public void theExceptionShouldHaveTheMessage(String expectedMessage) {
        Assertions.assertEquals(expectedMessage, exception.getMessage());
    }

    @And("I enter the numbers below to a list")
    public void iEnterTheNumbersBelowToAList(List<Integer> numbers) {
        calculator.setList(numbers);
    }

    @When("I iterate through the list to add all the even numbers")
    public void iIterateThroughTheListToAddAllTheEvenNumbers() {
        actual = calculator.addList();
    }

    @Then("the result should be {int}")
    public void theResultShouldBe(int expected) {
        Assertions.assertEquals(expected, actual);
    }
}
