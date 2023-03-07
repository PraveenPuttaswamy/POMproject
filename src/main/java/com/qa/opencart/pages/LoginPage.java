package com.qa.opencart.pages;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

    private WebDriver driver;
    private ElementUtil elUtil;

    private By emailId = By.id("input-email");
    private By password = By.id("input-password");
    private By loginBtn = By.xpath("//input[@value='Login']");
    private By forgotPwdLink = By.linkText("Forgotten Password");
    private By registerLink = By.linkText("Register");

    public LoginPage(WebDriver driver){
        this.driver = driver;
        elUtil = new ElementUtil(driver);
    }

    @Step("....Getting the login page URL....")
    public String getLoginpageUrl(){
        return elUtil.waitForURLContainsAndFetch(AppConstants.DEFAULT_SHORT_TIMEOUT,AppConstants.LOGIN_PAGE_PARTIAL_URL_VALUE);
    }

    @Step("....Getting the login page title....")
    public String getLoginPagetitle(){
        return elUtil.waitForTitleContainsAndFetch(AppConstants.DEFAULT_SHORT_TIMEOUT,AppConstants.LOGIN_PAGE_TITLE_VALUE);
    }

    @Step("....Checking if forgot password link exists....")
    public boolean isForgotPwdLinkExist(){
        return elUtil.waitForElementVisible(forgotPwdLink,AppConstants.DEFAULT_SHORT_TIMEOUT).isDisplayed();
    }


    @Step("....Login with username : {0} and password : {1}....")
    public AccountsPage doLogin(String un, String pwd){
        System.out.println("App credentials are : " + un + ":" + pwd);
        elUtil.waitForElementVisible(emailId,AppConstants.DEFAULT_MEDIUM_TIMEOUT).sendKeys(un);
        elUtil.doSendKeys(password,pwd);
        elUtil.doClick(loginBtn);
        return new AccountsPage(driver);
    }

    @Step("Navigating to registration page")
    public RegistrationPage navigateToRegisterPage() {
        elUtil.doClick(registerLink);
        return new RegistrationPage(driver);
    }
}
