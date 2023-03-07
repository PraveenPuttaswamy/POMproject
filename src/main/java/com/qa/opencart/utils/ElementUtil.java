package com.qa.opencart.utils;

import com.qa.opencart.factory.DriverFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ElementUtil {

    private static WebDriver driver;
    private  JavascriptUtil jsUtil;

    public ElementUtil(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getWebElement(By locator) {
        WebElement element = driver.findElement(locator);
        jsUtil = new JavascriptUtil(driver);
        if(Boolean.parseBoolean(DriverFactory.highlight)){
            jsUtil.flash(element);
        };
        return element;
    }

    public void doSendKeys(By locator, String value) {
        WebElement element  = getWebElement(locator);
        element.clear();
        element.sendKeys(value);
    }

    public void doClick(By locator) {
        getWebElement(locator).click();
    }

    public String doGetText(By locator) {
        return getWebElement(locator).getText();
    }

    public boolean checkElementIsDisplayed(By locator) {
        return getWebElement(locator).isDisplayed();
    }

    public String getElementAttribute(By locator, String attributeName) {
        return getWebElement(locator).getAttribute(attributeName);
    }

    public List<WebElement> getWebElements(By locator) {
        return driver.findElements(locator);
    }

    public void getWebElementAttributes(By locator, String attrName) {
        List<WebElement> eleList = getWebElements(locator);
        for (WebElement j : eleList
        ) {
            if (j.getAttribute("href") != null) {
                System.out.println(j.getAttribute(attrName));
            }
        }

    }

    public int getWebElementsCount(By locator){
        int eleCount =  getWebElements(locator).size();
        System.out.println("Total Elements Count " + locator + "-->" +eleCount);
        return eleCount;
    }

    public List<String> getElementsTextList(By locator){
        List<WebElement> eleList = getWebElements(locator);
        List<String> listString = new ArrayList<String>();

        for (WebElement e:eleList
             ) {
            listString.add(e.getText());
        }
        return listString;
    }

    public List<String> getElementsAttributeList(By locator, String attrName){
        List<WebElement> eleList = getWebElements(locator);
        List<String> listString = new ArrayList<String>();

        for (WebElement e:eleList
        ) {
            listString.add(e.getAttribute(attrName));
        }
        return listString;
    }


    //******Select Based dropdown Utils********************
    public void selectDropdownByVisibleText(By locator, String visibleText){
        Select select = new Select(getWebElement(locator));
        select.selectByVisibleText(visibleText);
    }

    public void selectDropdownByIndex(By locator, int index){
        Select select = new Select(getWebElement(locator));
        select.selectByIndex(index);
    }

    public void selectDropdownByValue(By locator, String value){
        Select select = new Select(getWebElement(locator));
        select.selectByValue(value);
    }

    public List<WebElement> getSelectDropdownOptions(By locator){
        Select select = new Select(getWebElement(locator));
        return select.getOptions();
    }

    public int getSelectDropdownTotalOptions(By locator){
        Select select = new Select(getWebElement(locator));
        return (select.getOptions().size());
    }

    public List<String> getSelectDropdownOptionsTextList(By locator){
        List<WebElement> optionsList =  getSelectDropdownOptions(locator);
        List<String> textList = new ArrayList<String>();

        for (WebElement i: optionsList
        ) {
            textList.add(i.getText());
        }
        return textList;
    }

    public void selectDropdownItemWithoutUsingSelect(By locator, String value){
        List<WebElement> optionsList = getWebElements(locator);
        for (WebElement i: optionsList
             ) {
            if(i.getText().equals(value)){
                i.click();
                break;
            }
        }

    }

    public void doSearch(By suggestListLocator, String suggestName){

        List<WebElement> elList = getWebElements(suggestListLocator);
        System.out.println(elList.size());

        for (WebElement el: elList
        ) {
            String txt = (el.getText());
            if((txt.contains(suggestName)) && (txt.length() > 0) ){
                el.click();
                break;
            }
        }
    }


    /**********************************Wait Util****************************/

    /**
     * An expectation for checking that an element is present on the DOM of a page.
     * 	This does not necessarily mean that the element is visible
     * @param locator
     * @param timeout
     * @return WebElement
     */
    public WebElement waitForElementPresence(By locator, int timeout){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    /**
     * An expectation for checking that an element is present on the DOM of a page
     * and visible. Visibility means that the element is not only displayed but also
     * has a height and width that is greater than 0.
     * @param locator
     * @param timeout
     * @return  WebElement
     */
    public WebElement waitForElementVisible(By locator, int timeout){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public Alert waitForAlertPresence(int timeout){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.alertIsPresent());
    }

    public String getAlertText(int timeout){
        return waitForAlertPresence(timeout).getText();
    }

    public void acceptAlert(int timeOut) {
        waitForAlertPresence(timeOut).accept();
    }

    public void dismissAlert(int timeOut) {
        waitForAlertPresence(timeOut).dismiss();
    }

    public void alertSendKeys(int timeOut, String value) {
        waitForAlertPresence(timeOut).sendKeys(value);
    }

    public String waitForTitleContainsAndFetch(int timeOut, String titleFractionValue) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        wait.until(ExpectedConditions.titleContains(titleFractionValue));
        return driver.getTitle();
    }

    public String waitForTitleIsAndFetch(int timeOut, String titleValue) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        wait.until(ExpectedConditions.titleIs(titleValue));
        return driver.getTitle();
    }

    public String waitForURLContainsAndFetch(int timeOut, String urlFractionValue) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        wait.until(ExpectedConditions.urlContains(urlFractionValue));
        return driver.getCurrentUrl();
    }

    public String waitForURLIsAndFetch(int timeOut, String urlValue) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        wait.until(ExpectedConditions.urlToBe(urlValue));
        return driver.getCurrentUrl();
    }

    public boolean waitForURLContains(int timeOut, String urlFractionValue) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        return wait.until(ExpectedConditions.urlContains(urlFractionValue));
    }

    public List<WebElement> waitForElementsPresence(By locator, int timeout){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    public List<WebElement> waitForElementsVisible(By locator, int timeout){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    public void waitForFrameAndSwitchToItByIDorName(int timeout, String idOrName){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(idOrName));
    }

    public void waitForFrameAndSwitchToItByIndex(int timeout, int frameIndex){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
    }

    public void waitForFrameAndSwitchToItByFrameElement(int timeout, WebElement frameElement){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));
    }

    public void waitForFrameAndSwitchToItByFrameLocator(int timeout, By locator){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
    }

    public void clickWhenReady(int timeout, By locator){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    public WebElement waitForElementToBeClickable(int timeout, By locator){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public void doClickWithActionsAndWait(int timeout, By locator){
        WebElement ele = waitForElementToBeClickable(timeout, locator);
        Actions act = new Actions(driver);
        act.click(ele).build().perform();
    }

    public void waitForNumberOfWindows(int timeout, int windowCount){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        wait.until(ExpectedConditions.numberOfWindowsToBe(windowCount));
    }

    public WebElement waitForElementPresenceWithFluentWait(int timeout, int pollingTime, By locator){
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(timeout))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .pollingEvery(Duration.ofSeconds(pollingTime))
                .withMessage("....element not found in the page");
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));

    }

    public void doActionsSendKeys(By locator, String value) {
        Actions act = new Actions(driver);
        act.sendKeys(getWebElement(locator), value).build().perform();
    }

    public void doActionsClick(By locator) {
        Actions act = new Actions(driver);
        act.click(getWebElement(locator)).build().perform();
    }
}


