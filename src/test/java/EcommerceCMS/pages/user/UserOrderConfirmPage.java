package EcommerceCMS.pages.user;

import keywords.WebUI;
import org.openqa.selenium.By;
import org.openqa.selenium.support.Color;
import org.testng.Assert;

import java.awt.*;

public class UserOrderConfirmPage {

    private By headerOrderConfirm = By.xpath("//h3[normalize-space()='5. Confirmation']");
    private By messageOrderSuccess = By.xpath("//span[normalize-space()='Your order has been placed successfully']");



    public void verifyOrderConfirmPage(){
        String colorExpected = "#e62e04";
        String currentURL = WebUI.getCurrentURL();

        String colorRGBAHeader = WebUI.getElementCssValue(headerOrderConfirm, "color");
        String colorActual = Color.fromString(colorRGBAHeader).asHex();


        Assert.assertEquals(colorActual, colorExpected, "color header not match");
        Assert.assertTrue(WebUI.isDisplayed(messageOrderSuccess), "message order success is not displayed");
        Assert.assertTrue(currentURL.contains("order-confirmed"), "url incorrect");

    }

}
