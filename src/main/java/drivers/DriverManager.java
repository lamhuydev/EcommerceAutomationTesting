package drivers;

import org.openqa.selenium.WebDriver;

public class DriverManager {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

//    private DriverManager() {
//
//    }

    public static void setDriver(WebDriver driver){
        DriverManager.driver.set(driver);
    }

    public static WebDriver getDriver(){
//        WebDriver driver = DriverManager.driver.get();
//        return driver;

        return driver.get();
    }

    public static void quitDriver() {
        DriverManager.driver.get().quit();
        driver.remove();
    }

}