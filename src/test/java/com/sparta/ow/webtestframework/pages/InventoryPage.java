package com.sparta.ow.webtestframework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class InventoryPage {
    private WebDriver webDriver;
    private By items = new By.ByClassName("inventory_item");
    private By itemToBasket1 = new By.ById("add-to-cart-sauce-labs-backpack");
    private By itemToBasket2 = new By.ById("add-to-cart-sauce-labs-bike-light");
   // private By basketCount = new By.ByCssSelector("shopping-cart-badge");
   private By basketCount = By.cssSelector("[data-test='shopping-cart-badge']");


    public InventoryPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }
    public int getNumberOfInventoryItems(){
        return webDriver.findElements(items).size();
    }
    public void addItemToBasket1(){  webDriver.findElement(itemToBasket1).click();    }
    public void addItemToBasket2(){  webDriver.findElement(itemToBasket2).click();    }
    public Integer getBasketCount(){ return Integer.valueOf(webDriver.findElement(basketCount).getText()); }
}
