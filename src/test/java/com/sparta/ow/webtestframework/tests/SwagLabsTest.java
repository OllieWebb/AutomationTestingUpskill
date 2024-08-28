package com.sparta.ow.webtestframework.tests;

import com.sparta.ow.webtestframework.pages.Website;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.is;

public class SwagLabsTest extends TestSetup {

    private static final String BASE_URL = "https://www.saucedemo.com/";
    private Website website;

    public static ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--headless");
        options.addArguments("--remote-allow-origins=");
        options.setImplicitWaitTimeout(Duration.ofSeconds(10));
        return options;
    }

    @Test
    @DisplayName("Given I enter a valid username and password, when I click login, then I should land on the inventory page")
    public void successfulLogin() {
       website = getWebsite(BASE_URL);
       website.getHomePage().enterUsername("standard_user");
       website.getHomePage().enterPassword("secret_sauce");
       website.getHomePage().clickLoginButton();

       MatcherAssert.assertThat(website.getCurrentUrl(), is(BASE_URL + "inventory.html"));
    }

    @Test
    @DisplayName("given i enter valid username and invalid password, when i clokc login, i should get an error message")
    public void unsuccessfulLogin(){
        website = getWebsite(BASE_URL);
        website.getHomePage().enterUsername("standard_user");
        website.getHomePage().enterPassword("wrong_password");
        website.getHomePage().clickLoginButton();

        MatcherAssert.assertThat(website.getHomePage().getErrorMessage(),Matchers.containsString("Epic sadface:"));
    }
    @Test
    @DisplayName("Given i login, when i arrive on the inventory page, i should see 6 items")
    public void viewSixItems(){
        website = getWebsite(BASE_URL);
        website.getHomePage().enterUsername("standard_user");
        website.getHomePage().enterPassword("secret_sauce");
        website.getHomePage().clickLoginButton();

        MatcherAssert.assertThat(website.getInventoryPage().getNumberOfInventoryItems(), Matchers.is(6));
    }

    @Test
    @DisplayName("Given i am on the inventory page, when i click on 'add to basket', i should see the item's in the basket increase by 1")
    public void addToBasket(){
        website = getWebsite(BASE_URL);
        website.getHomePage().enterUsername("standard_user");
        website.getHomePage().enterPassword("secret_sauce");
        website.getHomePage().clickLoginButton();

        website.getInventoryPage().addItemToBasket1();
        MatcherAssert.assertThat(website.getInventoryPage().getBasketCount(), Matchers.is(1));
        website.getInventoryPage().addItemToBasket2();
        MatcherAssert.assertThat(website.getInventoryPage().getBasketCount(), Matchers.is(2));
    }
}
