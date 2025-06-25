package keywords;

import com.aventstack.extentreports.Status;
import io.qameta.allure.Step;
import org.apache.poi.xslf.usermodel.XSLFTableCell;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import drivers.DriverManager;
import reports.AllureManager;
//import reports.//ExtentTestManager;
import utils.LogUtils;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.util.List;
import java.util.Set;

public class WebUI {

    private static int TIMEOUT = 10;
    private static double STEP_TIME = 0.2;
    private static int PAGE_LOAD_TIMEOUT = 20;


    public static void sleep(double second) {
        try {
            Thread.sleep((long) (1000 * second));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    // Lưu window hiện tại
    public static String getCurrentWindowHandle() {
        return DriverManager.getDriver().getWindowHandle();
    }

    // Lấy toàn bộ các window đang mở
    public static Set<String> getAllWindowHandles() {
        return DriverManager.getDriver().getWindowHandles();
    }

    // Chờ đến khi có số tab lớn hơn giá trị cho trước
    public static void waitForNewTabOpened(int currentTabCount) {
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(10));
        wait.until(driver -> driver.getWindowHandles().size() > currentTabCount);
    }

    // Chuyển sang tab khác (mặc định là tab mới)
    public static void switchToNewTab(String oldWindowHandle) {
        for (String windowHandle : getAllWindowHandles()) {
            if (!windowHandle.equals(oldWindowHandle)) {
                DriverManager.getDriver().switchTo().window(windowHandle);
                LogUtils.info("✅ Switched to new tab with URL: " + DriverManager.getDriver().getCurrentUrl());
                return;
            }
        }
        LogUtils.warn("⚠️ Không tìm thấy tab mới để switch.");
    }

    // Đóng tab hiện tại và quay về tab cũ
    public static void closeCurrentTabAndSwitchBack(String originalWindow) {
        DriverManager.getDriver().close();
        DriverManager.getDriver().switchTo().window(originalWindow);
        LogUtils.info("🔙 Switched back to original tab.");
    }


    public static void searchText(By by, String value) {
        try {
            waitForElementVisible(by);
            sleep(STEP_TIME);

            WebElement element = getWebElement(by);
            element.clear();
            element.sendKeys(value);

            // Nhấn Enter
            Actions action = new Actions(DriverManager.getDriver());
            action.moveToElement(element)
                    .sendKeys(Keys.ENTER)
                    .build()
                    .perform();

            LogUtils.info("🔍 Tìm kiếm với từ khóa [" + value + "] bằng element: " + by.toString());
        } catch (Exception e) {
            LogUtils.error("❌ Lỗi khi thực hiện thao tác searchText: " + e.getMessage());
        }
    }



    public static void uploadFileWithRobotClass(By elementFileForm, String filePath) {
        //Click để mở form upload
        WebUI.clickElement(elementFileForm);
        WebUI.sleep(2);

        // Khởi tạo Robot class
        Robot rb = null;
        try {
            rb = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }

        // Copy File path vào Clipboard
        StringSelection str = new StringSelection(filePath);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(str, null);

        // Nhấn Control+V để dán
        rb.keyPress(KeyEvent.VK_CONTROL);
        rb.keyPress(KeyEvent.VK_V);

        // Xác nhận Control V trên
        rb.keyRelease(KeyEvent.VK_CONTROL);
        rb.keyRelease(KeyEvent.VK_V);

        WebUI.sleep(1);

        // Nhấn Enter
        rb.keyPress(KeyEvent.VK_ENTER);
        rb.keyRelease(KeyEvent.VK_ENTER);

        WebUI.sleep(2);
    }


//    public static void acceptAlert() {
//        if (isAlertPresent()) {
//            DriverManager.getDriver().switchTo().alert().accept();
//        }
//    }
//
//    // Hàm kiểm tra sự tồn tại của alert
//    public static boolean isAlertPresent() {
//        try {
//            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(TIMEOUT));
//            wait.until(ExpectedConditions.alertIsPresent());
//            return true;
//        } catch (TimeoutException e) {
//            return false;
//        }
//    }

    @Step("Open URL: {0}")
    public static void openURL(String url) {
        DriverManager.getDriver().get(url);
        sleep(STEP_TIME);
        LogUtils.info("\uD83C\uDF10 Open URL: " + url);
        //ExtentTestManager.logMessage(Status.INFO, "\uD83C\uDF10 Open URL: " + url);

    }

    @Step("Get current URL")
    public static String getCurrentURL() {
        waitForPageLoaded();
        String currentURL = DriverManager.getDriver().getCurrentUrl();
        LogUtils.info("Get current url: " + currentURL);
        AllureManager.saveTextLog("==> Current URL " + currentURL);
        return currentURL;
    }

    public static WebElement findElement(By by) {
        waitForElementVisible(by);
        return DriverManager.getDriver().findElement(by);
    }

    public static WebElement findElementNonWait(By by) {
        return DriverManager.getDriver().findElement(by);
    }

    @Step("Find elements: {0}")
    public static List<WebElement> findElements(By by) {
        waitForElementVisible(by);
        return DriverManager.getDriver().findElements(by);
    }

//    public static String getChildText(WebElement parent, By childLocator) {
//        return parent.findElement(childLocator).getText();
//    }
//
//    public static String getChildAttribute(WebElement parent, By childLocator, String attr) {
//        return parent.findElement(childLocator).getAttribute(attr);
//    }


    public static void titleIs() {

    }


    @Step("Set text [ {1} ] on element: {0}")
    public static void setText(By by, String value) {
        waitForElementVisible(by);
        sleep(STEP_TIME);
        getWebElement(by).sendKeys(value);
        LogUtils.info("Set text [" + value + "] on element: " + by);
        //ExtentTestManager.logMessage(Status.PASS, "Set text [" + value + "] on element: " + by);
    }


    @Step("Click on element {0}")
    public static void clickElement(By by) {
        waitForElementClickable(by);
        sleep(STEP_TIME);
        getWebElement(by).click();
        LogUtils.info("Click on element " + by);
        //ExtentTestManager.logMessage(Status.PASS, "Click on element " + by);
    }

    @Step("Click on element {0} with time out: {1}")
    public static void clickElement(By by, int timeout) {
        waitForElementClickable(by, timeout);
        sleep(STEP_TIME);
        getWebElement(by).click();
        LogUtils.info("Click on element " + by);
        //ExtentTestManager.logMessage(Status.PASS, "Click on element " + by);
    }


    @Step("Clear text on element {0}")
    public static void clearText(By by) {
        waitForElementVisible(by);
        sleep(STEP_TIME);
        getWebElement(by).clear();
        LogUtils.info("Clear text on element " + by);
        //ExtentTestManager.logMessage(Status.PASS, "Clear text on element " + by);
    }


    @Step("Get text of element: {0}")
    public static String getText(By by) {
        waitForElementVisible(by);
        String text = getWebElement(by).getText();
        LogUtils.info("Get text of element: " + by + " || ==> Text Return: " + text);
        //ExtentTestManager.logMessage(Status.PASS, "Get text of element: " + by + " || ==> Text Return: " + text);

        AllureManager.saveTextLog("Get text of element: " + by + " || ==> Text Return: " + text);
        return text; //Trả về một giá trị kiểu String
    }

    @Step("Get title current page")
    public static String getTitle(){
        waitForPageLoaded();
        return DriverManager.getDriver().getTitle();
    }


    @Step("Get attribute of element {0}")
    public static String getElementAttribute(By by, String attributeName) {
        waitForElementVisible(by);
        LogUtils.info("Get attribute of element " + by);
        //ExtentTestManager.logMessage(Status.INFO, "Get attribute of element " + by);

        String value = getWebElement(by).getAttribute(attributeName);
        LogUtils.info("==> Attribute value: " + value);
        //ExtentTestManager.logMessage(Status.PASS, "==> Attribute value: " + value);

        AllureManager.saveTextLog("==> Attribute value: " + value);

        return value;
    }


    public static String getElementCssValue(By by, String cssPropertyName) {
        waitForElementVisible(by);
        LogUtils.info("Get CSS value " + cssPropertyName + " of element " + by);
        //ExtentTestManager.logMessage(Status.INFO, "Get CSS value " + cssPropertyName + " of element " + by);

        String value = getWebElement(by).getCssValue(cssPropertyName);
        LogUtils.info("==> CSS value: " + value);
        //ExtentTestManager.logMessage(Status.INFO, "==> CSS value: " + value);

        return value;
    }

    //Chờ đợi trang load xong mới thao tác
    public static void waitForPageLoaded() {
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(30), Duration.ofMillis(500));
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();

        //Wait for Javascript to load
        ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return js.executeScript("return document.readyState").toString().equals("complete");
            }
        };

        //Check JS is Ready
        boolean jsReady = js.executeScript("return document.readyState").toString().equals("complete");

        //Wait Javascript until it is Ready!
        if (!jsReady) {
            //System.out.println("Javascript is NOT Ready.");
            //Wait for Javascript to load
            try {
                wait.until(jsLoad);
            } catch (Throwable error) {
                error.printStackTrace();
                Assert.fail("FAILED. Timeout waiting for page load.");
            }
        }
    }


    @Step("Wait for element clickable: {0}")
    public static void waitForElementClickable(By by) {
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(TIMEOUT), Duration.ofMillis(500));
            wait.until(ExpectedConditions.elementToBeClickable(getWebElement(by)));
        } catch (Throwable error) {
            LogUtils.error("Timeout waiting for the element ready to click. " + by.toString());
            //ExtentTestManager.logMessage(Status.FAIL, "Timeout waiting for the element ready to click. " + by.toString());

            AllureManager.saveTextLog("Timeout waiting for the element ready to click. " + by.toString());

            Assert.fail("Timeout waiting for the element ready to click. " + by.toString());
        }
    }

    @Step("Wait for element clickable {0} with time out: {1}")
    public static void waitForElementClickable(By by, int timeOut) {
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(timeOut), Duration.ofMillis(500));
            wait.until(ExpectedConditions.elementToBeClickable(getWebElement(by)));
        } catch (Throwable error) {
            LogUtils.error("Timeout waiting for the element ready to click. " + by.toString());
            //ExtentTestManager.logMessage(Status.FAIL, "Timeout waiting for the element ready to click. " + by.toString());

            AllureManager.saveTextLog("Timeout waiting for the element ready to click. " + by.toString());

            Assert.fail("Timeout waiting for the element ready to click. " + by.toString());
        }
    }

    @Step("Wait for element visible {0}")
    public static void waitForElementVisible(By by) {
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(TIMEOUT), Duration.ofMillis(500));
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        } catch (Throwable error) {
            LogUtils.error("Timeout waiting for the element Visible. " + by.toString());
            //ExtentTestManager.logMessage(Status.FAIL, "Timeout waiting for the element Visible. " + by.toString());

            AllureManager.saveTextLog("Timeout waiting for the element Visible. " + by.toString());

            Assert.fail("Timeout waiting for the element Visible. " + by.toString());
        }
    }


    public static void waitForElementVisible(By by, int timeOut) {
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(timeOut), Duration.ofMillis(500));
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        } catch (Throwable error) {
            LogUtils.error("Timeout waiting for the element Visible. " + by.toString());
            //ExtentTestManager.logMessage(Status.FAIL ,"Timeout waiting for the element Visible. " + by.toString());

            Assert.fail("Timeout waiting for the element Visible. " + by.toString());
        }
    }


    public static void waitForElementPresent(By by) {
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(TIMEOUT), Duration.ofMillis(500));
            wait.until(ExpectedConditions.presenceOfElementLocated(by));
        } catch (Throwable error) {
            LogUtils.error("Element not exist. " + by.toString());
            //ExtentTestManager.logMessage(Status.FAIL ,"Element not exist. " + by.toString());

            Assert.fail("Element not exist. " + by.toString());
        }
    }

    public static void waitForElementPresent(By by, int timeOut) {
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(timeOut), Duration.ofMillis(500));
            wait.until(ExpectedConditions.presenceOfElementLocated(by));
        } catch (Throwable error) {
            LogUtils.error("Element not exist. " + by.toString());
            //ExtentTestManager.logMessage(Status.FAIL ,"Element not exist. " + by.toString());

            Assert.fail("Element not exist. " + by.toString());
        }
    }


    public static Boolean checkElementExist(By by) {
        waitForElementVisible(by);
        List<WebElement> listElement = getWebElements(by);

        if (listElement.size() > 0) {
            LogUtils.info("checkElementExist: " + true + " --- " + by);
            return true;
        } else {
            LogUtils.error("checkElementExist: " + false + " --- " + by);
            //ExtentTestManager.logMessage(Status.FAIL ,"checkElementExist: " + false + " --- " + by);

            return false;
        }
    }

    // Hàm kiểm tra sự tồn tại của phần tử với lặp lại nhiều lần
    public static boolean checkElementExist(By by, int maxRetries, int waitTimeMillis) {
        int retryCount = 0;

        while (retryCount < maxRetries) {
            try {
                WebElement element = getWebElement(by);
                if (element != null) {
                    LogUtils.info("Tìm thấy phần tử ở lần thử thứ " + (retryCount + 1));
                    //ExtentTestManager.logMessage(Status.INFO ,"Tìm thấy phần tử ở lần thử thứ " + (retryCount + 1));

                    return true; // Phần tử được tìm thấy
                }
            } catch (NoSuchElementException e) {
                LogUtils.error("Không tìm thấy phần tử. Thử lại lần " + (retryCount + 1));
                //ExtentTestManager.logMessage(Status.FAIL ,"Không tìm thấy phần tử. Thử lại lần " + (retryCount + 1));


                retryCount++;
                try {
                    Thread.sleep(waitTimeMillis); // Chờ trước khi thử lại
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            }
        }

        // Trả về false nếu không tìm thấy phần tử sau maxRetries lần
        LogUtils.error("Không tìm thấy phần tử sau " + maxRetries + " lần thử.");
        //ExtentTestManager.logMessage(Status.FAIL ,"Không tìm thấy phần tử sau " + maxRetries + " lần thử.");

        return false;
    }


    public static boolean isDisplayed(By by) {
        try {
            if (checkElementExist(by)) {
                waitForElementVisible(by);
                WebElement element = DriverManager.getDriver().findElement(by);
                return element.isDisplayed();
            }
        } catch (Exception e) {
            LogUtils.warn("⚠️ Không thể kiểm tra hiển thị của phần tử: " + by + " → " + e.getMessage());
        }
        return false;
    }

    public static boolean isElementEnabled(By by) {
        try {
            WebElement element = DriverManager.getDriver().findElement(by);
//            waitForElementVisible(by);
            boolean enabled = element.isEnabled();

            LogUtils.info("🔍 Trạng thái enabled của " + by + ": " + enabled);
            return enabled;
        } catch (Exception e) {
            LogUtils.error("❌ Không lấy được trạng thái enabled của " + by + " → " + e.getMessage());
            return false;
        }
    }

    public static boolean isElementSelected(By locator) {
        try {
            WebElement element = DriverManager.getDriver().findElement(locator);
            boolean selected = element.isSelected();
            LogUtils.info("🔍 Trạng thái selected của " + locator + ": " + selected);
            return selected;
        } catch (Exception e) {
            LogUtils.error("❌ Không lấy được trạng thái selected của " + locator + " → " + e.getMessage());
            return false;
        }
    }





    // JavascriptExecutor
    public static void clickElementWithScript(By by) {
        try {
            WebElement element = getWebElement(by);
            JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
            js.executeScript("arguments[0].scrollIntoView({behavior: 'instant', block: 'center'});", element);
            js.executeScript("arguments[0].click();", element);

            LogUtils.info("Click element with JavaScript: " + by);
            //ExtentTestManager.logMessage(Status.INFO, "Click element with JavaScript: " + by);
        } catch (Exception e) {
            LogUtils.error("❌ Không thể click element bằng script: " + by + " → " + e.getMessage());
            throw e;
        }
    }

    public static void scrollToElement(By by) {
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript("arguments[0].scrollIntoView(false);", getWebElement(by));
    }

    public static void scrollToElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript("arguments[0].scrollIntoView(false);", element);
    }

    public static void scrollToElementAtTop(By by) {
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript("arguments[0].scrollIntoView(true);", getWebElement(by));
    }

    public static void scrollToElementAtBottom(By by) {
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript("arguments[0].scrollIntoView(false);", getWebElement(by));
    }

    public static void scrollToElementAtTop(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public static void scrollToElementAtBottom(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript("arguments[0].scrollIntoView(false);", element);
    }

    public static void scrollToPosition(int X, int Y) {
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript("window.scrollTo(" + X + "," + Y + ");");
    }

    public static boolean moveToElement(By by) {
        try {
            Actions action = new Actions(DriverManager.getDriver());
            action.moveToElement(getWebElement(by)).release(getWebElement(by)).build().perform();
            return true;
        } catch (Exception e) {
            LogUtils.error(e.getMessage());
            return false;
        }
    }

    public static boolean moveToOffset(int X, int Y) {
        try {
            Actions action = new Actions(DriverManager.getDriver());
            action.moveByOffset(X, Y).build().perform();
            return true;
        } catch (Exception e) {
            LogUtils.error(e.getMessage());
            return false;
        }
    }

    public static boolean hoverElement(By by) {
        try {
            Actions action = new Actions(DriverManager.getDriver());
            action.moveToElement(getWebElement(by)).perform();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Hover to element: {0}")
    public static boolean mouseHover(By by) {
        try {
            Actions action = new Actions(DriverManager.getDriver());
            action.moveToElement(getWebElement(by)).perform();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean dragAndDrop(By fromElement, By toElement) {
        try {
            Actions action = new Actions(DriverManager.getDriver());
            action.dragAndDrop(getWebElement(fromElement), getWebElement(toElement)).perform();
            //action.clickAndHold(getWebElement(fromElement)).moveToElement(getWebElement(toElement)).release(getWebElement(toElement)).build().perform();
            return true;
        } catch (Exception e) {
            LogUtils.error(e.getMessage());
            return false;
        }
    }

    public static boolean dragAndDropElement(By fromElement, By toElement) {
        try {
            Actions action = new Actions(DriverManager.getDriver());
            action.clickAndHold(getWebElement(fromElement)).moveToElement(getWebElement(toElement)).release(getWebElement(toElement)).build().perform();
            return true;
        } catch (Exception e) {
            LogUtils.error(e.getMessage());
            return false;
        }
    }

    public static boolean dragAndDropOffset(By fromElement, int X, int Y) {
        try {
            Actions action = new Actions(DriverManager.getDriver());
            //Tính từ vị trí click chuột đầu tiên (clickAndHold)
            action.clickAndHold(getWebElement(fromElement)).pause(1).moveByOffset(X, Y).release().build().perform();
            return true;
        } catch (Exception e) {
            LogUtils.error(e.getMessage());
            return false;
        }
    }


    public static boolean clickEnterWithActions(By by) {
        try {
            WebElement element = getWebElement(by);
            Actions action = new Actions(DriverManager.getDriver());

            action.moveToElement(element)
                    .click()  // optional nếu cần focus
                    .sendKeys(Keys.ENTER)
                    .build()
                    .perform();

            LogUtils.info("✅ Nhấn Enter thành công vào element: " + by.toString());
            return true;
        } catch (Exception e) {
            LogUtils.error("❌ Lỗi khi nhấn Enter: " + e.getMessage());
            return false;
        }
    }



    public static void logConsole(Object message) {
        System.out.println(message);
    }

    public static WebElement getWebElement(By by) {
        return DriverManager.getDriver().findElement(by);
    }

    public static List<WebElement> getWebElements(By by) {
        return DriverManager.getDriver().findElements(by);
    }

}