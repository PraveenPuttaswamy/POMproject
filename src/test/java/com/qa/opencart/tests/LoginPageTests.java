package com.qa.opencart.tests;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;


@Epic("EPIC -100: Design login for open cart app")
@Story("US-Login: 101: Design login page features for open cart")
public class LoginPageTests extends BaseTest {

    @Severity(SeverityLevel.TRIVIAL)
    @Description("Getting the title of the Login page. Author: Praveen")
    @Test(priority = 1)
    public void LoginPageTitleTest(){
        String title = loginPage.getLoginPagetitle();
        Assert.assertEquals(title,AppConstants.LOGIN_PAGE_TITLE_VALUE);
    }

    @Severity(SeverityLevel.NORMAL)
    @Description("Getting the URL of the Login page. Author: Praveen")
    @Test(priority = 2)
    public void LoginPageUrlTest(){
        String URL = loginPage.getLoginpageUrl();
            Assert.assertTrue(URL.contains(AppConstants.LOGIN_PAGE_PARTIAL_URL_VALUE));
    }

    @Severity(SeverityLevel.CRITICAL)
    @Description("Checking if Forgot Password Link exist in the Login page. Author: Praveen")
    @Test(priority = 3)
    public void forgotPwdLinkTest(){
        Assert.assertTrue(loginPage.isForgotPwdLinkExist());
    }

    @Severity(SeverityLevel.BLOCKER)
    @Description("....Checking if user is able to login to Open Cart app using valid username and password.... Author: Praveen")
    @Test(priority = 4)
    public void loginTest(){
        acctPage = loginPage.doLogin(prop.getProperty("username").trim(), prop.getProperty("password").trim());
        Assert.assertTrue(acctPage.isLogoutLinkExist());
    }

}
