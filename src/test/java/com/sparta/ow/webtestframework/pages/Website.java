package com.sparta.ow.webtestframework.pages;

import net.bytebuddy.agent.builder.AgentBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Website {
    private WebDriver webDriver;
    private HomePage homePage;
    private InventoryPage inventoryPage;

    public Website(WebDriver driver) {
        this.webDriver = driver;
        homePage = new HomePage(webDriver);
        inventoryPage = new InventoryPage(webDriver);
    }

    public HomePage getHomePage(){
        return homePage;
    }
    public InventoryPage getInventoryPage() { return inventoryPage; }

    public String getCurrentUrl(){
        return webDriver.getCurrentUrl();
    }

    public String getPageTitle(){
        return webDriver.getTitle();
    }


}
