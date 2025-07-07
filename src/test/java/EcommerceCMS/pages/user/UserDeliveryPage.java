package EcommerceCMS.pages.user;

import io.qameta.allure.Step;
import keywords.WebUI;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.testng.Assert;
import utils.LogUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class UserDeliveryPage {

    private By headerDeliveryPage = By.xpath("//h3[normalize-space()='3. Delivery info']");
    private By activeProduct = By.xpath("//h5[normalize-space()='Active eCommerce CMS Products']");
    private By buttonPayment = By.xpath("//button[normalize-space()='Continue to Payment']");

    // product in delivery page
    private By productDeliveryPage = By.xpath("//li[@class='list-group-item']//span[@class='fs-14 opacity-60']");

    @Step("Action verify correct delivery page")
    public void verifyDeliveryPage() {
        LogUtils.info("Start verify delivery page");
        String colorExpected = "#e62e04";
        String currentURL = WebUI.getCurrentURL();
        String textHeaderDeliveryPage = WebUI.getText(headerDeliveryPage);
        String textActiveProduct = WebUI.getText(activeProduct);
        String rgbaHeaderColor = WebUI.getElementCssValue(headerDeliveryPage, "color");
        String hexActual = Color.fromString(rgbaHeaderColor).asHex();

        Assert.assertEquals(hexActual, colorExpected, "verifyDeliveryPage: color not match");
        Assert.assertEquals(textActiveProduct,"Active eCommerce CMS Products", "verifyDeliveryPage: text active product not match");
        Assert.assertTrue(WebUI.isDisplayed(activeProduct), "verifyDeliveryPage: active product is not display");
        Assert.assertTrue(textHeaderDeliveryPage.contains("Delivery"), "verifyDeliveryPage: text header not match");
        Assert.assertTrue(WebUI.isDisplayed(headerDeliveryPage), "verifyDeliveryPage: header is not display");
        Assert.assertTrue(currentURL.contains("delivery_info"), "verifyDeliveryPage: url not match");
        Assert.assertTrue(WebUI.isElementEnabled(buttonPayment), "verifyDeliveryPage: button payment is not enable");
        Assert.assertEquals(WebUI.getText(buttonPayment), "Continue to Payment", "verifyDeliveryPage: text button payment not match");
    }


    @Step("Action verify correct product delivery page")
    public void verifyProductDeliveryPage(List<Map<String, Object>> productData) {
        LogUtils.info("🚛 Start verify product in delivery page");
        List<WebElement> cartItems = WebUI.findElements(productDeliveryPage);

        for (WebElement item : cartItems) {
            String nameProductActual = item.getText().trim();
            LogUtils.info("📦 nameProductActual: " + nameProductActual);

//            Assert.assertTrue(
//                    productData.containsKey(nameProductActual),
//                    "❌ Không tìm thấy sản phẩm trong productData: " + nameProductActual
//            );

            boolean found = false;
            for (Map<String, Object> product : productData) {
                if (nameProductActual.equals(product.get("name"))) {
                    found = true;
                    break;
                }
            }

            Assert.assertTrue(
                    found,
                    "❌ Không tìm thấy sản phẩm trong productData: " + nameProductActual
            );
        }
    }

    @Step("Action click button payment to redirect payment page")
    public void clickPaymentPage(){
        WebUI.clickElement(buttonPayment);
    }

}
