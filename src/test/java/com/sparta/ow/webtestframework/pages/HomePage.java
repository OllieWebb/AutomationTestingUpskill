package com.sparta.ow.webtestframework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {
    private WebDriver webDriver;
    private By usernameField = new By.ByName("user-name");
    private By passwordField = new By.ByName("password");
    private By loginButton = new By.ById("login-button");
    private By errorMessage = By.cssSelector(".error-message-container h3");

    public HomePage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }
    public void enterUsername(String username){
        webDriver.findElement(this.usernameField).sendKeys(username);
    }
    public void enterPassword(String password){
        webDriver.findElement(this.passwordField).sendKeys(password);
    }
    public void clickLoginButton(){
        webDriver.findElement(this.loginButton).click();
    }
    public String getErrorMessage(){
        return webDriver.findElement(errorMessage).getText();
    }
}
