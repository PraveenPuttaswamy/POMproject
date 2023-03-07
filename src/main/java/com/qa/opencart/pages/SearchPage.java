package com.qa.opencart.pages;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SearchPage {


    private WebDriver driver;
    private ElementUtil elementUtil;

    private By searchProductResults = By.cssSelector("div#content div.product-layout");
    public SearchPage(WebDriver driver) {
        this.driver = driver;
        elementUtil = new ElementUtil(driver);
    }

    public String getSearchPageTitle(){
        return elementUtil.waitForTitleContainsAndFetch(AppConstants.DEFAULT_SHORT_TIMEOUT,AppConstants.SEARCH_PAGE_TITLE_VALUE);
    }

    public String getSearchPageURL(){
        return elementUtil.waitForURLContainsAndFetch(AppConstants.DEFAULT_SHORT_TIMEOUT,AppConstants.SEARCH_PAGE_PARTIAL_URL_VALUE);
    }

    public ProductInfoPage selectProduct(String productName){
        elementUtil.waitForElementVisible(By.linkText(productName), AppConstants.DEFAULT_SHORT_TIMEOUT).click();
        return new ProductInfoPage(driver);
    }

    public int getSearchProductsCount(){
        int productCount = elementUtil.waitForElementsVisible(searchProductResults,AppConstants.DEFAULT_SHORT_TIMEOUT).size();
        System.out.println("Product count : " + productCount);
        return productCount;
    }
}
