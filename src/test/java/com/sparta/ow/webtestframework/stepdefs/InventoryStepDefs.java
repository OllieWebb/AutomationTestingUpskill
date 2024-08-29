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

import java.io.IOException;

import static net.bytebuddy.matcher.ElementMatchers.is;

public class InventoryStepDefs {
    private Website website;

    private static final String BASE_URL = "https://www.saucedemo.com/";
    private static final String INVENTORY_PATH = "inventory.html/";


    @When("I land on the inventory page")
    public void iLandOnTheInventoryPage() {
        website = TestSetup.getWebsite(BASE_URL + INVENTORY_PATH);
    }
    @Then("The number of available products is {int}")
    public void theNumberOfAvailableProductsIs(int expected) {
        MatcherAssert.assertThat(website.getInventoryPage().getNumberOfInventoryItems(), Matchers.is(expected));

    }

    @When("I add an item to the cart")
    public void iAddAnItemToTheCart() {
        website.getInventoryPage().addItemToBasket1();

    }

    @Then("The item count increases by one")
    public void theItemCountIncreasesByOne() {
        MatcherAssert.assertThat(website.getInventoryPage().getBasketCount(), Matchers.is(1));

    }


}
