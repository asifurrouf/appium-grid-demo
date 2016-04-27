package io.appium.test.workshop;

import io.appium.java_client.MobileBy;
import io.appium.java_client.remote.MobileCapabilityType;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;



public class AndroidUnicodeTest extends AppiumTestClass {

    @BeforeTest
    public void setUp(String node) throws Exception {
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "5.0");
        capabilities.setCapability(MobileCapabilityType.APP, getApp("ApiDemos.apk"));
        capabilities.setCapability(MobileCapabilityType.APP_ACTIVITY, ".view.Controls1");
        capabilities.setCapability("unicodeKeyboard", true);
        super.setUpAndroidDriver(node);
    }

    @Test
    public void basicAndroidUnicodeTest() throws Exception {
        WebElement field = driver.findElement(MobileBy.className("android.widget.EditText"));
        String unicodeStr = "परीक्षा-परीक";
        field.sendKeys(unicodeStr);
        Assert.assertEquals(field.getText(), unicodeStr);
    }
}