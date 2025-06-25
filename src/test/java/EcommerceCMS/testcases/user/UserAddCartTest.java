package EcommerceCMS.testcases.user;

import Common.BaseTest;
import EcommerceCMS.pages.user.UserBasePage;
import EcommerceCMS.pages.user.UserLoginPage;
import EcommerceCMS.pages.user.UserProductListPage;
import io.qameta.allure.*;
import keywords.WebUI;
import org.testng.annotations.Test;

public class UserAddCartTest extends BaseTest {

    private UserLoginPage userLoginPage;
    private UserProductListPage userProductListPage;

    @Epic("User - Shopping Cart")
    @Feature("Add to Cart")
    @Story("Add multiple products to cart")
    @Owner("Huy")
    @Severity(SeverityLevel.CRITICAL)
    @Description("User adds two arbitrary products to the cart successfully")
    @Test
    public void testAddCart(){
        userLoginPage = new UserLoginPage();
        userProductListPage = new UserProductListPage();

        String nameProductSearch = "laptop pro game";

        // login
        userLoginPage.loginEcommercePage();

        // search product
        UserBasePage.userSearchProductWithClickButtonSearch(nameProductSearch);

        WebUI.sleep(1);

        // action add cart
        userProductListPage.verifyUserProductListPage(nameProductSearch);
        userProductListPage.addProductToCart();
    }
}
