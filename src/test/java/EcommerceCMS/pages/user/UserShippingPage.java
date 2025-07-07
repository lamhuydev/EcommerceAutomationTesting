package EcommerceCMS.pages.user;

import drivers.DriverManager;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import keywords.WebUI;
import org.openqa.selenium.By;
import org.testng.Assert;
import utils.LogUtils;

import java.util.List;

public class UserShippingPage {

    private By buttonShippingPage = By.xpath("//a[normalize-space()='Continue to Shipping']");
    private By headerShippingPage = By.xpath("//h3[contains(normalize-space(), 'Shipping info')]");
    private By optionAddressShippingPage = By.xpath("(//input[@name='address_id'])[1]/ancestor::label/parent::div");
    private By buttonAddNewAddressShippingPage = By.xpath("//div[text()='Add New Address']");
    private By buttonRedirectToDeliveryInfo = By.xpath("//button[normalize-space()='Continue to Delivery Info']");

    private By allOptionAddress = By.xpath("//input[@name='address_id']");

    @Step("Action click button shipping page")
    public void clickButtonShippingPage() {
        WebUI.clickElement(buttonShippingPage);
    }

    @Step("Action verify correct shipping page")
    public void verifyShippingPage() {
        LogUtils.info("🚛 Start verify shipping page");
        String currentURL = WebUI.getCurrentURL();
        String hexExpected = "#e62e04";
        String colorRGBAHeaderShippingPage = WebUI.getElementCssValue(headerShippingPage, "color");
        String hexActual = Color.fromString(colorRGBAHeaderShippingPage).asHex();

        LogUtils.info("colorHeaderShippingPage: " + hexActual);

        Assert.assertTrue(currentURL.contains("checkout"), "verifyShippingPage: url don't have checkout");
        Assert.assertEquals(hexActual, hexExpected, "verifyShippingPage: hex header shipping page not match");
        Assert.assertTrue(WebUI.isElementEnabled(optionAddressShippingPage));
        Assert.assertTrue(WebUI.isElementEnabled(buttonAddNewAddressShippingPage), "verifyShippingPage: button add new is not enable");
        Assert.assertTrue(WebUI.isElementEnabled(buttonRedirectToDeliveryInfo), "verifyShippingPage: button redirect delivery is not enable");
        Assert.assertTrue(WebUI.getText(buttonRedirectToDeliveryInfo).contains("Continue to Delivery Info"), "verifyShippingPage: text button redirect not match");
    }

    @Step("Action click option address")
    public void clickOptionAddress() {
        WebUI.sleep(1);

        List<WebElement> addressOptions = DriverManager.getDriver().findElements(allOptionAddress);

        boolean isAnyChecked = false;

        for (WebElement option : addressOptions) {
            if (option.isSelected()) {
                isAnyChecked = true;
                break;
            }
        }

        // Nếu chưa có option nào được chọn
        if (!isAnyChecked) {
            // Click vào option đầu tiên
//            WebUI.clickElement(addressOptions.get(0));
            WebUI.clickElement(optionAddressShippingPage);
            LogUtils.info("✅ Clicked on the first address option because none was selected.");
        } else {
            LogUtils.info("✅ An address option is already selected. No action taken.");
        }
    }

    @Step("Action click delivery page")
    public void clickDeliveryPage() {
        WebUI.scrollToElement(buttonRedirectToDeliveryInfo);
        WebUI.clickElement(buttonRedirectToDeliveryInfo);
    }

}
