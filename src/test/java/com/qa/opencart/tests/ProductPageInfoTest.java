package com.qa.opencart.tests;

import com.qa.opencart.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Map;

public class ProductPageInfoTest extends BaseTest {

    @BeforeClass
    public void productInfoPageSetup(){
        acctPage = loginPage.doLogin(prop.getProperty("username").trim(), prop.getProperty("password").trim());
    }


    @DataProvider
    public Object[][] getProductImagesData(){
        return new Object[][] {
                {"Macbook","MacBook Pro",4},
                {"iMac","iMac",3},
                {"Apple","Apple Cinema 30\"",6},
                {"Samsung","Samsung Galaxy Tab 10.1",7}
        };
    }

    @Test(dataProvider = "getProductImagesData")
    public void productImagesCountTest(String searchKey, String productName,int imageCount){
        searchPage = acctPage.performSearch(searchKey);
        productInfoPage = searchPage.selectProduct(productName);
        int actImagesCount = productInfoPage.getProductImagesCount();
        Assert.assertEquals(actImagesCount,imageCount);
    }

    @Test
    public void productInfoTest(){
        searchPage = acctPage.performSearch("MacBook");
        productInfoPage = searchPage.selectProduct("MacBook Pro");
        Map<String, String> actProductInfoMap = productInfoPage.getProductInfo();
        softAssert.assertEquals(actProductInfoMap.get("Brand"),"Apple");
        softAssert.assertEquals(actProductInfoMap.get("Product Code"),"Product 18");
        softAssert.assertEquals(actProductInfoMap.get("productname"),"MacBook Pro");
        softAssert.assertEquals(actProductInfoMap.get("productprice"),"$2,000.00");
        softAssert.assertAll();
    }


    @DataProvider
    public Object[][] getProductTestData(){
        return new Object[][] {
                {"Macbook","MacBook Pro"},
                {"iMac","iMac"},
                {"Samsung","Samsung Galaxy Tab 10.1"}
        };
    }

    @Test(dataProvider = "getProductTestData")
    public void addToCartTest(String searchKey, String productName){
        searchPage = acctPage.performSearch(searchKey);
        productInfoPage = searchPage.selectProduct(productName);
        productInfoPage.enterQuantity(1);
        String actSuccessMsg = productInfoPage.addProductToCart();
        softAssert.assertTrue(actSuccessMsg.contains("Success"));
        softAssert.assertTrue(actSuccessMsg.contains(productName));
        softAssert.assertAll();
    }
}
