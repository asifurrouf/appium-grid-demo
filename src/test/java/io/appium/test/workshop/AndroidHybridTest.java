package io.appium.test.workshop;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class AndroidHybridTest extends AppiumTestClass {

    @BeforeTest
    public void setUp(String node) throws Exception {
        capabilities.setCapability("deviceName", "Android Emulator");
        capabilities.setCapability("platformVersion", "4.4");
        capabilities.setCapability("app", getApp("HelloGappium-android.apk"));
        super.setUpAndroidDriver(node);
    }

    @Test
    public void basicAndroidHybridTest() throws Exception {
        runHybridTest();
    }
}