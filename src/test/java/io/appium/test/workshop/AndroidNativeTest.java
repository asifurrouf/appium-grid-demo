package io.appium.test.workshop;

import io.appium.java_client.MobileBy;
import io.appium.java_client.remote.MobileBrowserType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;


import java.util.List;

public class AndroidNativeTest extends AppiumTestClass {


    @Test
    public void basicAndroidNativeTest() throws Exception {
        driver.findElement(MobileBy.AccessibilityId("Add Contact")).click();
        List<WebElement> fields = driver.findElements(By.className("android.widget.EditText"));
        fields.get(0).sendKeys("My Name");
        fields.get(2).sendKeys("someone@somewhere.com");
        Assert.assertEquals(fields.get(0).getText(), "My Name");
        Assert.assertEquals(fields.get(2).getText(), "someone@somewhere.com");
        driver.navigate().back();
    }
}