package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import com.qa.opencart.exception.FrameworkException;
import org.aspectj.util.FileUtil;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.safari.SafariDriver;

public class DriverFactory {

    public WebDriver driver;
    public Properties prop;

    public OptionsManager optionsManager;

    public static String highlight;

    public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();


    /**
     * This method is initializing the driver based on the browser
     *
     * @param : Properties prop
     * @return WebDriver object reference
     */
    public WebDriver initDriver(Properties prop) {

        optionsManager = new OptionsManager(prop);
        String browserName = prop.getProperty("browser").toLowerCase().trim();
        System.out.println("Browser name is :" + browserName);
        highlight = prop.getProperty("highlight").trim();

        if (browserName.equalsIgnoreCase("chrome")) {
            tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
        } else if (browserName.equalsIgnoreCase("firefox")) {
            tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
        } else if (browserName.equalsIgnoreCase("safari")) {
            tlDriver.set(new SafariDriver());
        } else if (browserName.equalsIgnoreCase("edge")) {
            tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
        } else {
            System.out.println("Please enter the correct browser.....");
            throw new FrameworkException("NO BROWSER FOUND....");
        }

       getDriver().manage().deleteAllCookies();
        getDriver().manage().window().maximize();
        getDriver().get(prop.getProperty("url"));
        return getDriver();

    }

    /**
     * Get local thread copy of driver
     */
    public synchronized static WebDriver getDriver() {
        return tlDriver.get();
    }


    /**
     * This method is reading the properties from the .properties file
     *
     * @return properties file object reference
     */
    public Properties initProp() {

        prop = new Properties();
        FileInputStream ip = null;

        //Mvn clean install -Denv="PROD"
        String envName = System.getProperty("env");
        System.out.println("Running test cases on Env: " + envName);

        try {
            if (envName == null) {
                System.out.println("No environment is passed....Running tests on qa environment");
                ip = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\config\\qa.config.properties");
            } else {

                switch (envName.toLowerCase().trim()) {
                    case "qa":
                        ip = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\config\\qa.config.properties");
                        break;
                    case "dev":
                        ip = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\config\\dev.config.properties");
                        break;
                    case "stage":
                        ip = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\config\\stage.config.properties");
                        break;
                    case "prod":
                        ip = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\config\\config.properties");
                        break;
                    default:
                        System.out.println("Wrong environment is passed. No need to run the test cases");
                        throw new FrameworkException("WRONG ENV IS PASSED......");
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            prop.load(ip);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return prop;
    }

    /**
     * Take screenshot
     */
    public static String getScreenshot() {
        File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
        String path = System.getProperty("user.dir") + "\\screenshot\\" + System.currentTimeMillis() + ".png";
        File destination = new File(path);
        try {
            FileUtil.copyFile(srcFile, destination);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }
}
