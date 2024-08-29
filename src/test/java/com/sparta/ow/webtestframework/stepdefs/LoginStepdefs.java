package com.sparta.ow.webtestframework.stepdefs;

import com.sparta.ow.webtestframework.pages.Website;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.regex.Matcher;

import static org.hamcrest.Matchers.containsString;

public class LoginStepdefs {
    private Website website;

    private static final String BASE_URL = "https://www.saucedemo.com/";
    private int initialBasketCount;

    @After
    public void afterEach(){
        TestSetup.quitWebDriver();
        TestSetup.stopService();
    }

    @Before
    public static void setup() throws IOException {
        TestSetup.startChromeService();
        TestSetup.createWebDriver();
    }

    @Given("I am on the home page")
    public void iAmOnTheHomePage() {
        website = TestSetup.getWebsite(BASE_URL);
    }

    @And("I have entered the username {string}")
    public void iHaveEnteredTheUsername(String username) {
        website.getHomePage().enterUsername(username);
    }

    @And("I have entered the password {string}")
    public void iHaveEnteredThePassword(String password) {
        website.getHomePage().enterPassword(password);
    }

    @When("I click the login button")
    public void iClickTheLoginButton() {
        website.getHomePage().clickLoginButton();
    }

    @Then("I should land on the inventory page")
    public void iShouldLandOnTheInventoryPage() {
        MatcherAssert.assertThat(website.getCurrentUrl(), containsString("/inventory.html"));
    }

    @Then("I should see an error message that contains {string}")
    public void iShouldSeeAnErrorMessageThatContains(String expected) {
        MatcherAssert.assertThat(website.getHomePage().getErrorMessage(), containsString(expected));
    }


}
