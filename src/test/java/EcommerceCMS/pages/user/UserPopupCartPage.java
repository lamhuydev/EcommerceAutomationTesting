package EcommerceCMS.pages.user;

import io.qameta.allure.Step;
import keywords.WebUI;
import org.openqa.selenium.By;
import org.testng.Assert;

public class UserPopupCartPage {

    private int countProductCart = 0;

    // element of popup cart
    private By removeProductInPopupCart = By.xpath("//div[@id='cart_items']//button");
    private By quantityProductInPopupCart = By.xpath(".//span[contains(text(),'x')]");
    private By priceProductInPopupCart = By.xpath(".//span[contains(text(),'$')]");
    private By totalPriceInPopupCart = By.xpath("//span[normalize-space()='Subtotal']/following-sibling::span");
    private By headerPopupCart = By.xpath("//div[normalize-space()='Cart Items']");
    private By subTotalPopupCart = By.xpath("//span[normalize-space()='Subtotal']");
    private By buttonViewCartPopupCart = By.xpath("//a[normalize-space()='View cart']");
    private By buttonCheckoutPopupCart = By.xpath("//a[normalize-space()='Checkout']");
    private By buttonPopupCart = By.xpath("//div[@id='cart_items']");

    // cart count
    private By cartCount = By.xpath("//div[@id='cart_items']//span[contains(@class, 'badge') and contains(@class, 'cart-count')]");

    @Step("Action verify correct popup cart")
    public void verifyPopupCart(){
        WebUI.isDisplayed(headerPopupCart);
        WebUI.isDisplayed(subTotalPopupCart);
        WebUI.isDisplayed(buttonViewCartPopupCart);

        Assert.assertTrue(WebUI.getText(headerPopupCart).equals("Cart Items"), "verifyPopupCart: open popup cart fail");
    }

    @Step("Action click to popup cart")
    public void clickToPopupCart() {
        WebUI.clickElement(buttonPopupCart);
    }

    @Step("Action check count cart")
    public void checkCountCart() {
        int cartCountCheck = Integer.parseInt(WebUI.getText(cartCount));
        if (cartCountCheck > 0) {
            for (int i = 0; i < cartCountCheck; i++) {
                clickToPopupCart();
                WebUI.sleep(1);
                WebUI.clickElement(removeProductInPopupCart);
            }
        }
    }

    @Step("Action click button view cart")
    public void clickButtonViewCart(){
        WebUI.clickElement(buttonViewCartPopupCart);
    }

}
