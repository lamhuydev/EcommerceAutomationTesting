package EcommerceCMS.pages.user;

import org.openqa.selenium.support.Color;
import keywords.WebUI;
import org.openqa.selenium.By;
import org.testng.Assert;
import utils.LogUtils;

public class UserShippingPage {

    private By buttonShippingPage = By.xpath("//a[normalize-space()='Continue to Shipping']");
    private By headerShippingPage = By.xpath("//h3[contains(normalize-space(), 'Shipping info')]");
    private By optionAddressShippingPage = By.xpath("(//input[@name='address_id'])[8]");
    private By buttonAddNewAddressShippingPage = By.xpath("//div[text()='Add New Address']");
    private By buttonRedirectToDeliveryInfo = By.xpath("//button[normalize-space()='Continue to Delivery Info']");


    public void clickButtonShippingPage() {
        WebUI.clickElement(buttonShippingPage);
    }

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

    public void clickOptionAddress() {
        WebUI.sleep(1);
//        WebUI.scrollToElement(optionAddressShippingPage);
        WebUI.clickElementWithScript(optionAddressShippingPage);
    }

    public void clickDeliveryPage() {
        WebUI.scrollToElement(buttonRedirectToDeliveryInfo);
        WebUI.clickElement(buttonRedirectToDeliveryInfo);
    }

}
