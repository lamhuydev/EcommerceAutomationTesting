package EcommerceCMS.pages.user;

import keywords.WebUI;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.testng.Assert;
import utils.LogUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class UserPaymentPage {

    private By headerPaymentPage = By.xpath("//h3[normalize-space()='4. Payment']");
    private By buttonCompleteOrder = By.xpath("//button[normalize-space()='Complete Order']");
    private By inputAgree = By.xpath("//input[@id='agree_checkbox']/ancestor::label[@class='aiz-checkbox']");
    private By allItemProduct = By.xpath("//div[@id='cart_summary']//tr[@class='cart_item']");
    private By summaryItems = By.xpath("//h3[normalize-space()='Summary']/following::span[contains(normalize-space(), 'Items')]");
    private By priceProduct = By.xpath("//tr[@class='cart_item']//span");
    private By totalShipping = By.xpath("//th[normalize-space()='Total Shipping']//following-sibling::td/span");
    private By subTotal = By.xpath("//th[normalize-space()='Subtotal']/following-sibling::td/span");
    private By total = By.xpath("//tr[@class='cart-total']/td//span");

    public void verifyPaymentPage(){
        LogUtils.info("🚛 Start verify payment page");
        String colorExpected = "#e62e04";
        String currentURL = WebUI.getCurrentURL();
        String colorRGBAHeader = WebUI.getElementCssValue(headerPaymentPage, "color");
        String colorActual = Color.fromString(colorRGBAHeader).asHex();

        Assert.assertEquals(WebUI.getText(buttonCompleteOrder), "Complete Order", "button complete text not match");
        Assert.assertTrue(WebUI.isElementEnabled(buttonCompleteOrder), "button complete is not enable");
        Assert.assertEquals(colorActual, colorExpected, "header color not match");
        Assert.assertTrue(currentURL.contains("payment_select"), "url not match");
    }

    public void verifyProductPaymentPage(Map<String, Map<String, Object>> productData) {
        LogUtils.info("🚛 Start verify product in payment page");

        List<WebElement> cartItems = WebUI.findElements(allItemProduct);

        // assert count product
        String countProductRaw = WebUI.getText(summaryItems);
        String[] countProductFormat = countProductRaw.split(" ");
        int countProductActual = Integer.parseInt(countProductFormat[0].trim());

        int countProductExpected = productData.size();

        LogUtils.info("countProductActual: " + countProductActual);
        LogUtils.info("countProductExpected: " + countProductExpected);

        Assert.assertEquals(countProductActual, countProductExpected, "count product not match");

        double subTotalExpected = 0;

        for (WebElement item : cartItems) {

            // 👉 Lấy tên sản phẩm
            WebElement nameElement = item.findElement(By.cssSelector(".product-name"));
            String quantityText = nameElement.findElement(By.cssSelector(".product-quantity")).getText().trim();
            String nameProductActual = nameElement.getText().replace(quantityText, "").trim();

            // 👉 Lấy số lượng
            String quantityRaw = item.findElement(By.cssSelector(".product-quantity")).getText().trim();
            int quantityProductActual = Integer.parseInt(quantityRaw.replaceAll("[^0-9]", "").trim());

            // 👉 Lấy giá sản phẩm
            String priceRaw = item.findElement(By.cssSelector(".product-total")).getText().trim();
            double priceProductActual = Double.parseDouble(priceRaw.replace("$", "").trim());

            // 👉 Tính subtotal từng dòng
            subTotalExpected +=  priceProductActual;


            LogUtils.info("🔍 Sản phẩm: " + nameProductActual);
            LogUtils.info("   Số lượng: " + quantityProductActual);
            LogUtils.info("   Giá: " + priceProductActual);
//            LogUtils.info("   Tổng dòng: " + lineTotal);

            LogUtils.info("productData" + productData.get(nameProductActual));

            double minPriceProductExpected = (double) productData.get(nameProductActual).get("minPrice");
            double maxPriceProductExpected = (double) productData.get(nameProductActual).get("maxPrice");

            double minTotalPriceEachProductExpected = (double)  productData.get(nameProductActual).get("minTotal");
            double maxTotalPriceEachProductExpected = (double)  productData.get(nameProductActual).get("maxTotal");


            // 👉 Assert info product actual and expected
            Assert.assertTrue(productData.containsKey(nameProductActual), "❌ Không tìm thấy sản phẩm: " + nameProductActual);
            Assert.assertEquals(quantityProductActual,(int) productData.get(nameProductActual).get("quantity"));
            Assert.assertTrue(
                    priceProductActual == minTotalPriceEachProductExpected || priceProductActual == maxTotalPriceEachProductExpected ||
                            priceProductActual >= minTotalPriceEachProductExpected && priceProductActual <= maxTotalPriceEachProductExpected,
                    "price product actual not match with expected"
            );

        }

        // 👉 So sánh tổng subtotal sau khi duyệt xong toàn bộ
        String subTotalText = WebUI.getText(subTotal);
        double actualSubTotal = Double.parseDouble(subTotalText.replace("$", "").replace(",", "").trim());

        // assert total price all product
        String totalShippingRaw = WebUI.getText(totalShipping);
        double totalShippingActual = Double.parseDouble(totalShippingRaw.replace("$", "").replace(",", "").trim());

        String totalRaw = WebUI.getText(total);
        double totalActual = Double.parseDouble(totalRaw.replace("$", "").replace(",", "").trim());

        double totalExpected = totalShippingActual + actualSubTotal;

        LogUtils.info("💰 Tổng tiền mong đợi (calculated): " + subTotalExpected);
        LogUtils.info("💰 Tổng tiền hiển thị UI: " + actualSubTotal);


        Assert.assertEquals(actualSubTotal, subTotalExpected, "❌ Tổng tiền không khớp với subtotal!");
        Assert.assertEquals(totalActual, totalExpected, "total price all product not match");

        LogUtils.info("verifyProductPaymentPage: success!");
    }


    public void clickButtonComplete() {
        WebUI.clickElement(buttonCompleteOrder);
    }


    public void clickInputAgree(){
        WebUI.clickElement(inputAgree);
    }

}
