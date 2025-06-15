package EcommerceCMS.pages.user;

import keywords.WebUI;
import org.openqa.selenium.By;
import org.testng.Assert;
import utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

public class UserProductListPage {

    private int countProductCart = 0;

    private By product_1 = By.xpath("(//form[@id='search-form']/descendant::div[@class='col'])[1]");
    private By product_2 = By.xpath("(//form[@id='search-form']/descendant::div[@class='col'])[2]");

    // name product in product list page
    private By nameProduct_1 = By.xpath("(//form[@id='search-form']/descendant::div[@class='col'])[1]/descendant::div[contains(@class, 'rating')]/following-sibling::h3/a");
    private By nameProduct_2 = By.xpath("(//form[@id='search-form']/descendant::div[@class='col'])[2]/descendant::div[contains(@class, 'rating')]/following-sibling::h3/a");


    // name product in popup add cart
    private By nameProductPopup = By.xpath("//div[@id='addToCart-modal-body']//h2");

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

    public void verifyUserProductListPage(String nameProductSearch){
        WebUI.waitForPageLoaded();
        String currentUrl = WebUI.getCurrentURL();

        // Trích xuất slug từ URL
        String[] parts = currentUrl.split("/");
        String urlSlug = parts[parts.length - 1];

        String[] nameTagURLSlug = urlSlug.split("\\?");
        String[] keywordTagURLSlug = nameTagURLSlug[1].split("=");
        String[] productNameSearch =  keywordTagURLSlug[1].split("\\+");


        Assert.assertEquals(nameTagURLSlug[0], "search", "verifyUserProductListPage: redirect incorrect, name tag url not match");
        Assert.assertEquals(keywordTagURLSlug[0], "keyword", "verifyUserProductListPage: redirect incorrect, keyword tag url not match");


        // ghép mảng thành chuỗi : ["laptop", "lenovo"] => "laptop lenovo"
        String formatProductNameSearch = String.join(" ", productNameSearch);
        Assert.assertEquals(formatProductNameSearch.trim().toLowerCase(), nameProductSearch.trim().toLowerCase(),
                "Từ khóa tìm kiếm không khớp");

        LogUtils.info("verifyUserProductListPage: Passed - This Is Product List Page");
    }

    // add 2 product cùng lúc
    public void addProductToCart(){

        // add product 1
        WebUI.hoverElement(product_1);
        WebUI.sleep(0.5);
        WebUI.clickElement(buttonAddCartProduct_1);
        verifyPopupAddCart();
        clickButtonAddCartPopup();
        WebUI.sleep(0.5);
        verifyAddCartSuccess();
        clickClosePopupAddCart();

        WebUI.sleep(1);

        // add product 2
        WebUI.hoverElement(product_2);
        WebUI.sleep(1);
        WebUI.clickElement(buttonAddCartProduct_2);
        verifyPopupAddCart();
        clickButtonAddCartPopup();
        WebUI.sleep(0.5);
        verifyAddCartSuccess();
        clickClosePopupAddCart();


        LogUtils.info("addProductToCart: success");
    }

    public void verifyPopupAddCart(){
        String getNameProduct_1 = WebUI.getText(nameProduct_1);
        String getNameProduct_2 = WebUI.getText(nameProduct_2);
        String getNameProductPopup = WebUI.getText(nameProductPopup);

        List<String> allNameProduct = new ArrayList<>();
        allNameProduct.add(getNameProduct_1);
        allNameProduct.add(getNameProduct_2);

        Assert.assertTrue(allNameProduct.contains(getNameProductPopup),
                "❌ verifyPopupAddCart: popup add cart name product not match with name list product");

        LogUtils.info("✅ verifyPopupAddCart: verify success !");
    }

    public void verifyAddCartSuccess(){

        if(WebUI.isDisplayed(messageAddCartSuccess) && WebUI.isDisplayed(relatedProductSuggestion)){
            countProductCart++;
        }


        Assert.assertTrue(WebUI.isDisplayed(messageAddCartSuccess), "verifyAddCartSuccess: add cart fail, message is not display");
        Assert.assertTrue(WebUI.isDisplayed(relatedProductSuggestion), "verifyAddCartSuccess: add cart fail, relate product suggestion is not display");

        // assert current count product
        int getCartCount = Integer.parseInt(WebUI.getText(cartCount));

        LogUtils.info("verifyAddCartSuccess countProductCart: " + countProductCart);
        LogUtils.info("verifyAddCartSuccess getCartCount: " + getCartCount);

        Assert.assertEquals(countProductCart, getCartCount, "verifyAddCartSuccess: count add not match");

        LogUtils.info("verifyAddCartSuccess: add cart success!");

    }

    public void clickButtonAddCartPopup(){
        WebUI.clickElement(buttonAddCartPopup);
        LogUtils.info("Click button add cart in popup add cart");
    }

    public void clickClosePopupAddCart(){
        WebUI.clickElement(buttonClosePopupAddCart);
        LogUtils.info("Click button close popup add cart");
    }


}
