package EcommerceCMS.pages.user;

import io.qameta.allure.Step;
import keywords.WebUI;
import org.openqa.selenium.By;
import org.testng.Assert;
import utils.LogUtils;

public class UserDetailProductPage {

    private By textNameProduct = By.xpath("//div[@class='text-left']/h1");
    private By textPriceProduct = By.xpath("(//div[@class='text-left']//strong)[1]");
    private By textUnitProduct = By.xpath("(//div[@class='text-left']//strong)[1]/following-sibling::span");
    private By textQuantityProduct = By.xpath("//span[@id='available-quantity']");
    private By textDescription = By.xpath("//div[@id='tab_default_1']//p");


    @Step("Verify correct product detail page")
    public void verifyProductDetailPage(String hrefExpected){
        LogUtils.info("start verifyProductDetailPage");
        String currentURL = WebUI.getCurrentURL();
        String title = WebUI.getTitle();

        LogUtils.info("currentURL verify product detail page: " + currentURL);
        LogUtils.info("href expected verify product detail page: " + hrefExpected);
        LogUtils.info("title verify product detail page: " + title);

        String nameProduct = WebUI.getText(textNameProduct);

        LogUtils.info("href expected verify product detail page: " + hrefExpected);
        LogUtils.info("title verify product detail page: " + title);

        Assert.assertEquals(currentURL, hrefExpected, "verify failed: url not match");
        Assert.assertEquals(title, nameProduct, "title page not match");
    }

    @Step("Verify info product detail in product detail page with excel data")
    public void verifyInfoProductDetail(String productName, String unit, String price, String quantity, String description){

        String nameProduct = WebUI.getText(textNameProduct);
        String priceProductRaw = WebUI.getText(textPriceProduct);
        String priceProduct = priceProductRaw.replace("$", "").replace(",", "").replace(".00", "").trim();

        String unitProductRaw = WebUI.getText(textUnitProduct);
        String unitProduct = unitProductRaw.replace("/", "").trim();

        String quantityProduct = WebUI.getText(textQuantityProduct);
        String descriptionProduct = WebUI.getText(textDescription);


        LogUtils.info("🔍 nameProduct UI: " + nameProduct + " | expected: " + productName);
        LogUtils.info("🔍 priceProduct UI: " + priceProduct + " | expected: " + price);
        LogUtils.info("🔍 unitProduct UI: " + unitProduct + " | expected: " + unit);
        LogUtils.info("🔍 quantityProduct UI: " + quantityProduct + " | expected: " + quantity);
        LogUtils.info("🔍 descriptionProduct UI: " + descriptionProduct + " | expected: " + description);


        Assert.assertEquals(nameProduct, productName, "❌ Name product is not match with excel data");
        Assert.assertEquals(priceProduct, price, "❌ Price product is not match with excel data");
        Assert.assertEquals(unitProduct, unit, "❌ Unit product is not match with excel data");
        Assert.assertEquals(quantityProduct, quantity, "❌ Quantity product is not match with excel data");
        Assert.assertEquals(descriptionProduct, description, "❌ Description product is not match with excel data");

        LogUtils.info("✅ Verify info product detail with excel data successfully !");

    }

}
