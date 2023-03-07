package com.qa.opencart.pages;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.tracing.opentelemetry.SeleniumSpanExporter;

import java.util.ArrayList;
import java.util.List;

public class AccountsPage {

    private WebDriver driver;
    private ElementUtil elementUtil;

    private By logoutLink = By.linkText("Logout");
    private By search = By.name("search");
    private By accsHeaders = By.cssSelector("div#content h2");

    private By searchtextbox = By.name("search");

    private By searchIcon = By.cssSelector("#search button");

    public AccountsPage(WebDriver driver){
        this.driver = driver;
        elementUtil = new ElementUtil(driver);
    }

    public String getAccPageTitle(){
        return elementUtil.waitForTitleContainsAndFetch(AppConstants.DEFAULT_SHORT_TIMEOUT,AppConstants.ACCOUNTS_PAGE_TITLE_VALUE);
    }

    public String getAccPageURL(){
        return elementUtil.waitForURLContainsAndFetch(AppConstants.DEFAULT_SHORT_TIMEOUT,AppConstants.ACCOUNTS_PAGE_PARTIAL_URL_VALUE);
    }

    public boolean isLogoutLinkExist(){
        return elementUtil.waitForElementVisible(logoutLink,AppConstants.DEFAULT_SHORT_TIMEOUT).isDisplayed();
    }

    public boolean isSearchExist(){
        return elementUtil.waitForElementVisible(search,AppConstants.DEFAULT_SHORT_TIMEOUT).isDisplayed();
    }

    public List<String> getAcctsPageHeaders(){
        List<WebElement> headerList =  elementUtil.waitForElementsVisible(accsHeaders,AppConstants.DEFAULT_SHORT_TIMEOUT);
        List<String>  accHeaderValList = new ArrayList<String>();
        for (WebElement e: headerList
             ) {
            accHeaderValList.add(e.getText());
        }
        return accHeaderValList;
    }

    public SearchPage performSearch(String searchkey){

        if (isSearchExist()){
            elementUtil.doSendKeys(search, searchkey);
            elementUtil.doClick(searchIcon);
            return new SearchPage(driver);
        }
        else{
            System.out.println("Search field is not present...");
            return null;
        }

    }
}
