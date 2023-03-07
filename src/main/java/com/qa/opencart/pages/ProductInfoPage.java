package com.qa.opencart.pages;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ProductInfoPage {

    private WebDriver driver;
    private ElementUtil elementUtil;

    private Map<String,String> productInfoMap;

    private By productHeader = By.cssSelector("div h1");
    private By productImages = By.cssSelector("ul.thumbnails img");

    private By productMetadata = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[position()=1]/li");

    private By productPricedata = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[position()=2]/li");

    private By cartSuccessMsg = By.cssSelector("div.alert.alert-success");
    private By quantity  =By.id("input-quantity");

    private By adToCartBtn = By.id("button-cart");

    public ProductInfoPage(WebDriver driver) {
        this.driver = driver;
        elementUtil = new ElementUtil(driver);
    }

    public String getProductHeaderValue(){
        String header = elementUtil.doGetText(productHeader);
        return header;
    }

    public int getProductImagesCount(){
        int imageCount = elementUtil.waitForElementsVisible(productImages, AppConstants.DEFAULT_MEDIUM_TIMEOUT).size();
        System.out.println("Product Images Count : " + imageCount);
        return  imageCount;
    }


    public Map<String,String> getProductInfo(){
        productInfoMap =  new LinkedHashMap<String,String>();
        productInfoMap.put("productname",getProductHeaderValue());
        getProductMetaData();
        getProductPriceData();
        System.out.println(productInfoMap);
        return productInfoMap;
    }

    private void getProductMetaData(){

        List<WebElement> metaList = elementUtil.waitForElementsVisible(productMetadata,AppConstants.DEFAULT_SHORT_TIMEOUT);

        for (WebElement e: metaList
        ) {
            String meta = e.getText();
            String[] metaInfoArray = meta.split(":");
            String key = metaInfoArray[0].trim();
            String value = metaInfoArray[1].trim();
            productInfoMap.put(key,value);
        }
    }

    private void getProductPriceData(){
        List<WebElement> priceList = elementUtil.waitForElementsVisible(productPricedata,AppConstants.DEFAULT_SHORT_TIMEOUT);
        String price = priceList.get(0).getText();
        String exTax = priceList.get(1).getText();
        String exTaxValue = exTax.split(":")[1].trim();

        productInfoMap.put("productprice",price);
        productInfoMap.put("exTax",exTaxValue);
    }

    public void enterQuantity(int qty){
        System.out.println("Product quantity : " + qty);
        elementUtil.doSendKeys(quantity,String.valueOf(qty));
    }

    public String addProductToCart(){
        elementUtil.doClick(adToCartBtn);
        String successMsg = elementUtil.waitForElementVisible(cartSuccessMsg,AppConstants.DEFAULT_SHORT_TIMEOUT).getText();
        System.out.println("Cart Success Message : "+successMsg);
        return successMsg;
    }

}
