package selenium;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class DemoQATests {
    private static final String DRIVER_LOCATION = "src/test/resources/chromedriver.exe";
    private static final String BASE_URL = "https://demoqa.com/droppable/";
    private static ChromeDriverService service;

    private WebDriver webDriver;

    private static ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        //options.addArguments("--headless");
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
        MatcherAssert.assertThat(webDriver.getCurrentUrl(), Matchers.is(BASE_URL));
    }

    @Test
    @DisplayName("move the box and assert dropped text appears")
    public void moveTheBoxAndAssertDroppedTextAppears() {
        webDriver.get(BASE_URL);
        {
            WebElement element = webDriver.findElement(By.id("draggable"));
            ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(true);", element);

        }
        WebElement source = webDriver.findElement(By.id("draggable"));
        WebElement target = webDriver.findElement(By.id("droppable"));
        Actions builder = new Actions(webDriver);
        builder.dragAndDrop(source, target).perform();

        webDriver.findElement(By.id("draggable")).click();
        webDriver.findElement(By.cssSelector("#simpleDropContainer p")).click();
        MatcherAssert.assertThat(webDriver.findElement(By.cssSelector("#simpleDropContainer p")).getText(), is("Dropped!"));
    }
    }

