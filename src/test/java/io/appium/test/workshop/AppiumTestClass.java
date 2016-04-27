package io.appium.test.workshop;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;


public abstract class AppiumTestClass {
    protected AppiumDriver driver;
    protected DesiredCapabilities capabilities = new DesiredCapabilities();
    @BeforeTest
    @Parameters({"deviceName","node"})
    public void setUp(String deviceName,String node) throws Exception {
        capabilities.setCapability("deviceName",deviceName);
        capabilities.setCapability("platformVersion", "5.0");
        capabilities.setCapability("app", getApp("ContactManager.apk"));

        setUpAndroidDriver(node);
    }

    protected String getApp(String appFile) {
        File projectRoot = new File(System.getProperty("user.dir"));
        File appDir = new File(projectRoot, "apps");
        File app = new File(appDir, appFile);
        return app.toString();
    }

    protected String getVersion(String defaultVersion) {
        String userVer = System.getenv("VER");
        if (userVer != null) {
            return userVer;
        }
        return defaultVersion;
    }

    protected IOSDriver setUpIosDriver(String appiumServer) throws Exception {
        preDriverInit();
        capabilities.setCapability("platformName", "iOS");
        driver = new IOSDriver(new URL(appiumServer), capabilities);
        postDriverInit();
        return (IOSDriver) driver;
    }

    protected AndroidDriver setUpAndroidDriver(String appiumServer ) throws Exception {
        preDriverInit();
        capabilities.setCapability("platformName", "Android");
        driver = new AndroidDriver(new URL(appiumServer), capabilities);
        postDriverInit();

        System.out.println("------------------------------------");
        System.out.println(appiumServer + ":start!");
        return (AndroidDriver) driver;
    }

    protected void preDriverInit() {
    }

    protected void postDriverInit() throws Exception {
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    protected void runWebTest() throws Exception {
        driver.get("http://saucelabs.com/test/guinea-pig");
        WebElement idElement = driver.findElement(By.id("i_am_an_id"));
        Assert.assertNotNull(idElement);
        Assert.assertEquals("I am a div", idElement.getText());
        WebElement commentElement = driver.findElement(By.id("comments"));
        Assert.assertNotNull(commentElement);
        commentElement.sendKeys("This is an awesome comment");
        WebElement submitElement = driver.findElement(By.id("submit"));
        Assert.assertNotNull(submitElement);
        submitElement.click();
        Thread.sleep(2000);
        WebElement yourCommentsElement = driver.findElement(By.id("your_comments"));
        Assert.assertNotNull(yourCommentsElement);
        Assert.assertTrue(driver.findElement(By.id("your_comments")).getText().contains("This is an awesome comment"));
    }

    protected void switchToWebview() throws Exception {
        Set<String> contexts = driver.getContextHandles();
        // => ['NATIVE_APP', 'WEBVIEW_1', ...]
        // make sure we have something other than the native context
//        Assert.(contexts.size(), greaterThan(1));
        for (String context : contexts) {
            if (!context.equals("NATIVE_APP")) {
                driver.context(context);
                break;
            }
        }
    }

    protected void runHybridTest() throws Exception {
        switchToWebview();
        WebElement search = driver.findElement(By.cssSelector(".search-key"));
        search.sendKeys("j");
        List<WebElement> employees = driver.findElements(By.cssSelector(".topcoat-list a"));
        Assert.assertEquals(employees.size(), 5);
        employees.get(3).click();
        List<WebElement> options = driver.findElements(By.cssSelector(".actions a"));
        Assert.assertEquals(options.size(), 6);
        options.get(3).click();
        Thread.sleep(2000);
    }
    @AfterTest
    @Parameters({})
    public void tearDown() throws Exception {
        System.out.println("test run complete");
        driver.quit();
    }
}
