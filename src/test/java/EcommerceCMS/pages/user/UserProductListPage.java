package EcommerceCMS.pages.user;

import io.qameta.allure.Step;
import keywords.WebUI;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import utils.LogUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserProductListPage {

    private UserCartPage userCartPage;
    private UserPopupCartPage userPopupCartPage;
    private UserShippingPage userShippingPage;
    private UserDeliveryPage userDeliveryPage;
    private UserPaymentPage userPaymentPage;
    private UserOrderConfirmPage userOrderConfirmPage;

    private int countProductCart = 0;

    private By product_1 = By.xpath("(//form[@id='search-form']/descendant::div[@class='col'])[1]");
    private By product_2 = By.xpath("(//form[@id='search-form']/descendant::div[@class='col'])[2]");

    // name product in product list page
    private By nameProduct_1 = By.xpath("(//form[@id='search-form']/descendant::div[@class='col'])[1]/descendant::div[contains(@class, 'rating')]/following-sibling::h3/a");
    private By nameProduct_2 = By.xpath("(//form[@id='search-form']/descendant::div[@class='col'])[2]/descendant::div[contains(@class, 'rating')]/following-sibling::h3/a");

    // button popup cart
    private By buttonPopupCart = By.xpath("//div[@id='cart_items']");

    // element of popup cart
    private By removeProductInPopupCart = By.xpath("//div[@id='cart_items']//button");
    private By quantityProductInPopupCart = By.xpath(".//span[contains(text(),'x')]");
    private By priceProductInPopupCart = By.xpath(".//span[contains(text(),'$')]");
    private By totalPriceInPopupCart = By.xpath("//span[normalize-space()='Subtotal']/following-sibling::span");
    private By headerPopupCart = By.xpath("//div[normalize-space()='Cart Items']");
    private By subTotalPopupCart = By.xpath("//span[normalize-space()='Subtotal']");
    private By buttonViewCartPopupCart = By.xpath("//a[normalize-space()='View cart']");
    private By buttonCheckoutPopupCart = By.xpath("//a[normalize-space()='Checkout']");

    // list item cart element
    private By cartItemElements = By.xpath("//div[@id='cart_items']//li[@class='list-group-item']");

    // element product in popup add cart
    private By nameProductPopupAddCart = By.xpath("//div[@id='addToCart-modal-body']//h2");
    private By priceProductPopupAddCart = By.xpath("(//div[@id='addToCart-modal-body']//strong)[1]");
    private By quantityProductPopupAddCart = By.xpath("//input[@name='quantity']");
    private By colorProductPopupAddCart = By.xpath("//input[@name='color' and @checked]");

    // button add cart when hover prodcut
    private By buttonAddCartProduct_1 = By.xpath("((//form[@id='search-form']/descendant::div[@class='col'])[1]/descendant::a[@data-title='Add to cart'])[1]");
    private By buttonAddCartProduct_2 = By.xpath("((//form[@id='search-form']/descendant::div[@class='col'])[2]/descendant::a[@data-title='Add to cart'])[1]");

    // button add cart when click buttonAddCartProduct_1
    private By buttonAddCartPopup = By.xpath("//span[normalize-space()='Add to cart']");

    // button close popup add cart
    private By buttonClosePopupAddCart = By.xpath("//div[@id='addToCart']/descendant::button[contains(@class, 'close') and @aria-label='Close']");


    // text add to cart success
    private By messageAddCartSuccess = By.xpath("//h3[normalize-space()='Item added to your cart!']");

    private By relatedProductSuggestion = By.xpath("//span[normalize-space()='Frequently Bought Together']");

    // cart count
    private By cartCount = By.xpath("//div[@id='cart_items']//span[contains(@class, 'badge') and contains(@class, 'cart-count')]");


    // add 2 product cụ thể
    // flow: search product > product list page displayed > hover product > click button add cart
    @Step("Action add product to cart")
    public void addProductToCart() {
        userPopupCartPage = new UserPopupCartPage();
        userCartPage = new UserCartPage();
        userShippingPage = new UserShippingPage();
        userDeliveryPage = new UserDeliveryPage();
        userPaymentPage = new UserPaymentPage();
        userOrderConfirmPage = new UserOrderConfirmPage();


        // khai báo mảng productData
        // Map<String, Map<String, Object>> productData = new HashMap<>();
        List<Map<String, Object>> productData = new ArrayList<>();
        // check count cart
        // checkCountCart();
        userPopupCartPage.checkCountCart();

        // add product 1
        WebUI.hoverElement(product_1);
        WebUI.sleep(0.5);
        WebUI.clickElement(buttonAddCartProduct_1);
        verifyPopupAddCart();

        // add product to Map for action verify add cart success
        Map<String, Object> productInfo1 = getElementPopupAddCart();
        // productData.put((String) productInfo1.get("name"), productInfo1);
        productData.add(productInfo1);


        clickButtonAddCartPopup();
        WebUI.sleep(0.5);
        verifyPopupAddCartSuccess();


        clickClosePopupAddCart();

        WebUI.sleep(1);

        // add product 2
        WebUI.hoverElement(product_2);
        WebUI.sleep(1);
        WebUI.clickElement(buttonAddCartProduct_2);
        verifyPopupAddCart();

        // add product to Map for action verify add cart success
        Map<String, Object> productInfo2 = getElementPopupAddCart();
        // productData.put((String) productInfo2.get("name"), productInfo2);
        productData.add(productInfo2);


        clickButtonAddCartPopup();
        WebUI.sleep(0.5);
        verifyPopupAddCartSuccess();
        clickClosePopupAddCart();

        // clickToPopupCart();
        // verifyPopupCart();
        userPopupCartPage.clickToPopupCart();
        userPopupCartPage.verifyPopupCart();

        // verify popup cart
        verifyAddCartSuccess(productData);

        // click to cart page
        userPopupCartPage.clickButtonViewCart();

        // verify cart page
        userCartPage.verifyCartPage();
        userCartPage.verifyProductInCart(productData);

        // click shipping page
        userShippingPage.clickButtonShippingPage();

        // verify shipping page
        userShippingPage.verifyShippingPage();

        // click option address
        userShippingPage.clickOptionAddress();

        // click to delivery page
        userShippingPage.clickDeliveryPage();

        // verify delivery page
        userDeliveryPage.verifyDeliveryPage();

        // verify product in delivery page
        userDeliveryPage.verifyProductDeliveryPage(productData);

        // click payment page
        userDeliveryPage.clickPaymentPage();

        // verify payment page
        userPaymentPage.verifyPaymentPage();

        // verify product in payment page
        userPaymentPage.verifyProductPaymentPage(productData);

        // click agree terms
        userPaymentPage.clickInputAgree();

        // click complete order
        userPaymentPage.clickButtonComplete();

        // verify order confirmed page
        userOrderConfirmPage.verifyOrderConfirmPage();

        LogUtils.info("addProductToCart: success");
    }

    @Step("Action verify user prduct list page")
    public void verifyUserProductListPage(String nameProductSearch) {
        WebUI.waitForPageLoaded();
        String currentUrl = WebUI.getCurrentURL();

        // Trích xuất slug từ URL
        String[] parts = currentUrl.split("/");
        String urlSlug = parts[parts.length - 1];

        String[] nameTagURLSlug = urlSlug.split("\\?");
        String[] keywordTagURLSlug = nameTagURLSlug[1].split("=");
        String[] productNameSearch = keywordTagURLSlug[1].split("\\+");


        Assert.assertEquals(nameTagURLSlug[0], "search", "verifyUserProductListPage: redirect incorrect, name tag url not match");
        Assert.assertEquals(keywordTagURLSlug[0], "keyword", "verifyUserProductListPage: redirect incorrect, keyword tag url not match");


        // ghép mảng thành chuỗi : ["laptop", "lenovo"] => "laptop lenovo"
        String formatProductNameSearch = String.join(" ", productNameSearch);
        Assert.assertEquals(formatProductNameSearch.trim().toLowerCase(), nameProductSearch.trim().toLowerCase(),
                "Từ khóa tìm kiếm không khớp");

        LogUtils.info("verifyUserProductListPage: Passed - This Is Product List Page");
    }

    @Step("Action get element popup add cart")
    public Map<String, Object> getElementPopupAddCart() {
        String priceProductPopupFormat = WebUI.getText(priceProductPopupAddCart);
        String priceRange = priceProductPopupFormat.replace("$", "").replace(",", "");



        String[] prices = priceRange.split(" - ");

        LogUtils.info("🔍 Raw price (popup): " + priceProductPopupFormat);
        LogUtils.info("💡 Formatted price range: " + priceRange);
        for (int i = 0; i < prices.length; i++) {
            LogUtils.info("📌 prices[" + i + "]: " + prices[i]);
        }

        double minPrice;
        double maxPrice;

        if (prices.length == 2) {
            minPrice = Double.parseDouble(prices[0]);
            maxPrice = Double.parseDouble(prices[1]);
            LogUtils.info("💲 Giá min: " + minPrice + " | Giá max: " + maxPrice);
        } else if (prices.length == 1) {
            minPrice = Double.parseDouble(prices[0]);
            maxPrice = minPrice; // nếu không có khoảng thì giá min = max
            LogUtils.info("💲 Chỉ có một mức giá: " + minPrice);
        } else {
            LogUtils.error("❌ Không tách được giá hợp lệ từ chuỗi: " + priceRange);
            throw new RuntimeException("Giá sản phẩm không đúng định dạng");
        }


        int quantityProduct = Integer.parseInt(WebUI.getElementAttribute(quantityProductPopupAddCart, "value"));
        String productName = WebUI.getText(nameProductPopupAddCart);

        WebElement colorElement = WebUI.findElementNonWait(colorProductPopupAddCart); // KHÔNG dùng waitVisible
        String color = colorElement.getAttribute("value");

        Map<String, Object> productInfo = new HashMap<>();
        productInfo.put("name", productName);
        productInfo.put("minPrice", minPrice);
        productInfo.put("maxPrice", maxPrice);
        productInfo.put("quantity", quantityProduct);
        productInfo.put("color", color);
        productInfo.put("minTotal", minPrice * quantityProduct);
        productInfo.put("maxTotal", maxPrice * quantityProduct);


        return productInfo;
    }

    @Step("Action verify correct popup add cart")
    public void verifyPopupAddCart() {
        String getNameProduct_1 = WebUI.getText(nameProduct_1);
        String getNameProduct_2 = WebUI.getText(nameProduct_2);
        String getNameProductPopup = WebUI.getText(nameProductPopupAddCart);

        List<String> allNameProduct = new ArrayList<>();
        allNameProduct.add(getNameProduct_1);
        allNameProduct.add(getNameProduct_2);

        Assert.assertTrue(allNameProduct.contains(getNameProductPopup),
                "❌ verifyPopupAddCart: popup add cart name product not match with name list product");

        LogUtils.info("✅ verifyPopupAddCart: verify success !");
    }

    // flow: hover product > click button add cart > verify popup add cart
    @Step("Action verify popup add cart success")
    public void verifyPopupAddCartSuccess() {
        if (WebUI.isDisplayed(messageAddCartSuccess) && WebUI.isDisplayed(relatedProductSuggestion)) {
            countProductCart++;
        }

        Assert.assertTrue(WebUI.isDisplayed(messageAddCartSuccess), "verifyPopupAddCartSuccess: add cart fail, message is not display");
        Assert.assertTrue(WebUI.isDisplayed(relatedProductSuggestion), "verifyPopupAddCartSuccess: add cart fail, relate product suggestion is not display");

        // assert current count product
        int getCartCount = Integer.parseInt(WebUI.getText(cartCount));

        LogUtils.info("verifyPopupAddCartSuccess countProductCart: " + countProductCart);
        LogUtils.info("verifyPopupAddCartSuccess getCartCount: " + getCartCount);

        Assert.assertEquals(countProductCart, getCartCount, "verifyPopupAddCartSuccess: count add not match");
        LogUtils.info("verifyPopupAddCartSuccess: popup add cart success!");

    }


    // flow: click button cart > popup displayed > verify
    // for popup cart
    @Step("Action verify add cart success")
    public void verifyAddCartSuccess(List<Map<String, Object>> productData) {
        List<WebElement> cartItems = WebUI.findElements(cartItemElements);
        double totalAllProducts = 0;

        // actual product in cart popup
        for (WebElement item : cartItems) {
            String nameProductActual = item.findElement(By.cssSelector(".fw-600")).getText().trim();

            String quantityRaw = item.findElement(quantityProductInPopupCart).getText();
            int quantityProductActual = Integer.parseInt(quantityRaw.replace("x", "").trim());

            String priceRaw = item.findElement(priceProductInPopupCart).getText();
            double price = Double.parseDouble(priceRaw.replace("$", "").replace(",", "").trim());

            double subtotal = price * quantityProductActual;
            totalAllProducts += subtotal;

            LogUtils.info("📦 Product: " + nameProductActual);
            LogUtils.info("Quantity: " + quantityProductActual + ", Price: " + price + ", Subtotal: " + subtotal);

            // Tìm sản phẩm tương ứng trong danh sách productData
            Map<String, Object> matchedProduct = null;
            for (Map<String, Object> productInfo : productData) {
                String name = (String) productInfo.get("name");
                if (nameProductActual.equals(name)) {
                    matchedProduct = productInfo;
                    break;
                }
            }

            if (matchedProduct == null) {
                LogUtils.warn("⚠️ Sản phẩm '" + nameProductActual + "' không có trong dữ liệu đã lưu!");
                continue;
            }

            int expectedQty = (int) matchedProduct.get("quantity");
            double minPrice = (double) matchedProduct.get("minPrice");
            double maxPrice = (double) matchedProduct.get("maxPrice");

            try {
                Assert.assertEquals(quantityProductActual, expectedQty, "❌ Quantity not match for product: " + nameProductActual);
                Assert.assertTrue(
                        price >= minPrice && price <= maxPrice,
                        "❌ Price product is not in range expected for product: " + nameProductActual
                );
            } catch (AssertionError e) {
                LogUtils.error("❌ " + e.getMessage());
                productData.remove(matchedProduct); // xóa bản ghi trùng nếu cần
                throw e;
            }
        }

        // get total price actual in popup cart
        String totalText = WebUI.getText(totalPriceInPopupCart);
        double actualTotalPrice = Double.parseDouble(totalText.replace("$", "").replace(",", "").trim());

        LogUtils.info("🔢 Expected total: " + totalAllProducts + " | Actual total: " + actualTotalPrice);
        Assert.assertEquals(actualTotalPrice, totalAllProducts, 0.01, "❌ Total price in popup cart not match!");

        LogUtils.info("✅ verifyAddCartSuccess successfully! Sản phẩm còn lại: " + productData.size());
    }


    @Step("Action click button add cart popup")
    public void clickButtonAddCartPopup() {
        WebUI.clickElement(buttonAddCartPopup);
        LogUtils.info("Click button add cart in popup add cart");
    }

    @Step("Action click button close popup add cart")
    public void clickClosePopupAddCart() {
        WebUI.clickElement(buttonClosePopupAddCart);
        LogUtils.info("Click button close popup add cart");
    }

}
