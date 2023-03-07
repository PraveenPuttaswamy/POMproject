package com.qa.opencart.base;

import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.pages.*;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.asserts.SoftAssert;

import java.util.Properties;

public class BaseTest {

    private DriverFactory df;
    private WebDriver driver;
    protected LoginPage loginPage;
    protected AccountsPage acctPage;

    protected SearchPage searchPage;

    protected ProductInfoPage productInfoPage;
    protected Properties prop;

    protected ViewCartPopUpPage viewCartPopUpPage;

    protected RegistrationPage registerPage;

    protected SoftAssert softAssert;

    @BeforeTest
    public void setup(){

        df = new DriverFactory();
        prop = df.initProp();
        driver = df.initDriver(prop);
        loginPage = new LoginPage(driver);
        softAssert = new SoftAssert();
    }

    @AfterTest
    public void tearDown(){
        driver.quit();
    }

}
