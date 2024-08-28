package selenium;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.is;

public class SwagLabsTest {
    private static final String DRIVER_LOCATION = "src/test/resources/chromedriver.exe";
    private static final String BASE_URL = "https://www.saucedemo.com/";
    private static ChromeDriverService service;

    private WebDriver webDriver;

    public static ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
       // options.addArguments("--headless");
        options.addArguments("--remote-allow-origins=");
        options.setImplicitWaitTimeout(Duration.ofSeconds(10));
        return options;
    }

    @BeforeAll
    public static void beforeAll() throws IOException {
        service = new ChromeDriverService.Builder()
                .usingDriverExecutable(new File(DRIVER_LOCATION))
                .usingAnyFreePort()
                .build();
        service.start();
    }

    @AfterAll
    static void afterAll() {
        service.stop();
    }

    @BeforeEach
    public void setup() {
        webDriver = new RemoteWebDriver(service.getUrl(), getChromeOptions());
    }

    @AfterEach
    public void afterEach() {
        webDriver.quit();
    }

    @Test
    @DisplayName("Check that the webdriver works")
    public void checkWebDriver() {
        webDriver.get(BASE_URL);
        Assertions.assertEquals(BASE_URL, webDriver.getCurrentUrl());
        Assertions.assertEquals("Swag Labs", webDriver.getTitle());
    }

    @Test
    @DisplayName("Given I enter a valid username and password, when I click login, then I should land on the inventory page")
    public void successfulLogin(){
       // Wait<WebDriver> webDriverWait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

        webDriver.get(BASE_URL);
        WebElement usernameField = webDriver.findElement(By.name("user-name"));
        WebElement passwordField = webDriver.findElement(By.name("password"));
        WebElement loginButton = webDriver.findElement(By.id("login-button"));
        usernameField.sendKeys("standard_user");
        passwordField.sendKeys("secret_sauce");
        loginButton.click();
        //webDriverWait.until(driver -> driver.getCurrentUrl().contains("/inventory"));

        MatcherAssert.assertThat(webDriver.getCurrentUrl(), Matchers.is(BASE_URL + "inventory.html"));
    }

    @Test
    @DisplayName("Given I am logged in, when I view the inventory page, I should see the correct number of products")
    public void checkNumberOfProductsOnInventoryPage() throws IOException {
        webDriver.get(BASE_URL);
        WebElement usernameField = webDriver.findElement(By.name("user-name"));
        WebElement passwordField = webDriver.findElement(By.name("password"));
        WebElement loginButton = webDriver.findElement(By.id("login-button"));
        usernameField.sendKeys("standard_user");
        passwordField.sendKeys("secret_sauce");
        loginButton.click();
        List<WebElement> items = webDriver.findElements(By.className("inventory_item"));
        int itemsCount = items.size();

        try (PrintWriter writer = new PrintWriter(new FileWriter("products.txt"))) {
            for (WebElement item : items) {
                WebElement nameElement = item.findElement(By.className("inventory_item_name"));
                WebElement priceElement = item.findElement(By.className("inventory_item_price"));
                String itemInfo = nameElement.getText() + ": " + priceElement.getText();
                writer.println(itemInfo);
                System.out.println(itemInfo);
            }
        }

        MatcherAssert.assertThat(itemsCount, Matchers.is(6));
    }
    @Test
    @DisplayName("when i try login with a valid username and incorrect password, i get epic sadface error message")
    public void invalidLogin(){

        webDriver.get(BASE_URL);
        WebElement usernameField = webDriver.findElement(By.name("user-name"));
        WebElement passwordField = webDriver.findElement(By.name("password"));
        WebElement loginButton = webDriver.findElement(By.id("login-button"));
        usernameField.sendKeys("standard_user");
        passwordField.sendKeys("wrong");
        loginButton.click();

        WebElement errorMessage = webDriver.findElement(By.cssSelector(".error-message-container h3"));

        // Assert that the error message contains "Epic sadface:"
        MatcherAssert.assertThat(errorMessage.getText(), Matchers.containsString("Epic sadface:"));

    }
    @Test
    @DisplayName("Window handles")
    public void windowHandlesTest() {
        webDriver.get("https://news.ycombinator.com/");
        String originalTab = webDriver.getWindowHandle();
        System.out.println(originalTab);
        webDriver.findElement(By.linkText("new")).sendKeys(Keys.chord(Keys.CONTROL, Keys.ENTER));

        Set<String> allWindows = webDriver.getWindowHandles();
        System.out.println(allWindows);

        // switch the new tab (or at least one that is not this one)
        for (String tab : allWindows) {
            if (!originalTab.equals(tab)) {
                webDriver.switchTo().window(tab);
                break;
            }

        }
    }
    @Test
    @DisplayName("test items can be added to the basket")
    public void addTwoItemsToBasket(){
        webDriver.get(BASE_URL);
        WebElement usernameField = webDriver.findElement(By.name("user-name"));
        WebElement passwordField = webDriver.findElement(By.name("password"));
        WebElement loginButton = webDriver.findElement(By.id("login-button"));
        usernameField.sendKeys("standard_user");
        passwordField.sendKeys("secret_sauce");
        loginButton.click();

        WebElement addBasket1 = webDriver.findElement(By.id("add-to-cart-sauce-labs-backpack"));
        addBasket1.click();
        WebElement addBasket2 = webDriver.findElement(By.id("add-to-cart-sauce-labs-bike-light"));
        addBasket2.click();

        WebElement checkout = webDriver.findElement(By.id("shopping_cart_container"));
        checkout.click();

        List<WebElement> basketItems = webDriver.findElements(By.className("cart_item"));
        int itemsCount = basketItems.size();

        MatcherAssert.assertThat(itemsCount, Matchers.is(2));

    }
}
