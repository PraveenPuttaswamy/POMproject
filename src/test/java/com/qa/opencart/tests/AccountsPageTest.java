package com.qa.opencart.tests;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

public class AccountsPageTest extends BaseTest {

    @BeforeClass
    public void accPageSetup(){
        acctPage = loginPage.doLogin(prop.getProperty("username").trim(), prop.getProperty("password").trim());
    }

    @Test
    public void accountsPageTitleTest(){
        String title = acctPage.getAccPageTitle();
        Assert.assertEquals(title, AppConstants.ACCOUNTS_PAGE_TITLE_VALUE);
    }

    @Test
    public void accountsPageUrlTest(){
        String url = acctPage.getAccPageURL();
        Assert.assertTrue(url.contains(AppConstants.ACCOUNTS_PAGE_PARTIAL_URL_VALUE));
    }

    @Test
    public void isLogoutLinkExists(){
        Assert.assertTrue(acctPage.isLogoutLinkExist());
    }

    @Test
    public void accountsPageHeadersTest(){
       List<String> actualHeadersList =  acctPage.getAcctsPageHeaders();
        System.out.println("Accounts Page Headers List :" + actualHeadersList);
       Assert.assertEquals(actualHeadersList.size(),AppConstants.ACCOUNTS_PAGE_HEADER_COUNT);
    }


    @Test
    public void accountsPageHeadersValueTest(){
        List<String> actualHeadersList =  acctPage.getAcctsPageHeaders();
        System.out.println("Accounts Page Headers List :" + actualHeadersList);
        Assert.assertEquals(actualHeadersList,AppConstants.ACCOUNTS_PAGE_EXPECTED_HEADERS_LIST);
    }

    @DataProvider
    public Object[][] getProductData(){
        return new Object[][] {
                {"Macbook"},
                {"iMac"},
                {"Apple"},
                {"Samsung"}
        };
    }

    @Test(dataProvider = "getProductData")
    public void searchProductCountTest(String searchKey){
        searchPage = acctPage.performSearch(searchKey);
        Assert.assertTrue(searchPage.getSearchProductsCount()>0);
    }

    @DataProvider
    public Object[][] getProductTestData(){
        return new Object[][] {
                {"Macbook","MacBook Pro"},
                {"iMac","iMac"},
                {"Apple","Apple Cinema 30\""},
                {"Samsung","Samsung Galaxy Tab 10.1"}
        };
    }

    @Test (dataProvider = "getProductTestData")
    public void searchProductTest(String searchKey, String productName){
        searchPage = acctPage.performSearch(searchKey);
        if(searchPage.getSearchProductsCount()>0){
            productInfoPage = searchPage.selectProduct(productName);
            String actProductHeader = productInfoPage.getProductHeaderValue();
            Assert.assertEquals(actProductHeader,productName);
        }
    }

}
