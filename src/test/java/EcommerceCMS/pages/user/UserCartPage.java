package EcommerceCMS.pages.user;

import keywords.WebUI;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import utils.LogUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class UserCartPage {

    private By headerCartPage = By.xpath("//h3[contains(normalize-space(),'My Cart')]");
    private By subTotalCartPage = By.xpath("//section[@id='cart-summary']/descendant::span[normalize-space()='Subtotal']");
    private By totalPriceCartPage = By.xpath("//section[@id='cart-summary']/descendant::span[normalize-space()='Subtotal']/following-sibling::span");

    // list product in cart page
    private By listProductCartPage = By.xpath("//section[@id='cart-summary']//li[contains(@class, 'list-group-item')]");

    // element of product in cart page
    private By quantityProductCart = By.xpath(".//input[contains(@name, 'quantity')]");
    private By priceProductCart = By.xpath(".//span[normalize-space()='Price']/following-sibling::span");
    private By totalPriceEachProduct = By.xpath(".//span[normalize-space()='Total']/following-sibling::span");

    public void verifyCartPage() {
        String currentURL = WebUI.getCurrentURL();

        WebUI.isDisplayed(headerCartPage);
        WebUI.isDisplayed(subTotalCartPage);

        String getHeader = WebUI.getText(headerCartPage);
        String getSubtotal = WebUI.getText(subTotalCartPage);

//        Assert.assertEquals(getHeader, "My Cart", "verifyCartPage: header cart page not match");
        Assert.assertTrue(getHeader.contains("My Cart"), "verifyCartPage: header cart page not match");
        Assert.assertEquals(getSubtotal, "Subtotal", "verifyCartPage: subtotal in cart page not match");
        Assert.assertTrue(currentURL.contains("cart"), "verifyCartPage: url dont have cart char");
    }

    public void verifyProductInCart(Map<String, Map<String, Object>> productData) {
        List<WebElement> cartItems = WebUI.findElements(listProductCartPage);
        double totalAllProducts = 0;

        for (WebElement item : cartItems) {
            String nameProductActual = item.findElement(By.cssSelector(".fs-14")).getText().trim();
            int quantityProductActual = Integer.parseInt(item.findElement(quantityProductCart).getAttribute("value").trim());

            String priceRaw = item.findElement(priceProductCart).getText();
            double price = Double.parseDouble(priceRaw.replace("$", "").replace(",", "").trim());

            String totalPriceEachProductRaw = item.findElement(totalPriceEachProduct).getText();
            double getTotalEachProduct = Double.parseDouble(totalPriceEachProductRaw.replace("$", "").trim());

            double subtotal = price * quantityProductActual;
            totalAllProducts += subtotal;

            LogUtils.info("Total price each product actual: " + getTotalEachProduct + ", Total price each product expected: " + subtotal);
            Assert.assertEquals(getTotalEachProduct, subtotal, 0.01, "❌ Tổng tiền từng sản phẩm không đúng!");

            LogUtils.info("📦 Toàn bộ productData: " + productData.toString());
            LogUtils.info("🧾 Product: " + nameProductActual +
                    " | Qty: " + quantityProductActual +
                    " | Price: $" + price +
                    " | Subtotal: $" + subtotal);

            LogUtils.info("📦 Keys trong productData: " + productData.keySet());

            String[] nameProductDataKey = nameProductActual.split("-");

            if (!productData.containsKey(nameProductDataKey[0].trim())) {
                LogUtils.warn("⚠️ Sản phẩm '" + nameProductActual + "' không có trong dữ liệu đã lưu!");
                continue;
            }

            Map<String, Object> expected = productData.get(nameProductDataKey[0].trim());
            int expectedQty = (int) expected.get("quantity");
            double minPrice = (double) expected.get("minPrice");
            double maxPrice = (double) expected.get("maxPrice");

            LogUtils.info("🎯 Expected → Qty: " + expectedQty + " | Min: " + minPrice + " | Max: " + maxPrice);


            try {
                Assert.assertEquals(quantityProductActual, expectedQty, "❌ Quantity không đúng cho sản phẩm: " + nameProductActual);
                Assert.assertTrue(
                        price == minPrice || price == maxPrice ||
                                    price >= minPrice && price <= maxPrice
                        , "❌ Price không nằm trong khoảng cho sản phẩm: " + nameProductActual);
            } catch (AssertionError e) {
                LogUtils.error("❌ " + e.getMessage());
                productData.remove(nameProductActual);
                throw e;
            }
        }

        String totalText = WebUI.getText(totalPriceCartPage);
        double actualTotalPrice = Double.parseDouble(totalText.replace("$", "").replace(",", "").trim());

        LogUtils.info("🔢 Expected total: " + totalAllProducts + " | Actual total: " + actualTotalPrice);
        Assert.assertEquals(actualTotalPrice, totalAllProducts, 0.01, "❌ Tổng tiền giỏ hàng không khớp!");

        LogUtils.info("✅ verifyProductInCart hoàn tất! Còn lại trong productData: " + productData.keySet());
    }


}
