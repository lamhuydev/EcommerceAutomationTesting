package EcommerceCMS.testcases.user;

import Common.BaseTest;
import EcommerceCMS.pages.user.UserBasePage;
import EcommerceCMS.pages.user.UserLoginPage;
import EcommerceCMS.pages.user.UserProductListPage;
import keywords.WebUI;
import org.testng.annotations.Test;

public class UserAddCartTest extends BaseTest {

    private UserLoginPage userLoginPage;
    private UserProductListPage userProductListPage;


    @Test
    public void testAddCart(){
        userLoginPage = new UserLoginPage();
        userProductListPage = new UserProductListPage();

        String nameProductSearch = "laptop lenovo";

        // login
        userLoginPage.loginEcommercePage();

        // search product
        UserBasePage.userSearchProductWithClickButtonSearch(nameProductSearch);

        WebUI.sleep(1);

        // action add cart
        userProductListPage.verifyUserProductListPage(nameProductSearch);
        userProductListPage.addProductToCart();

        WebUI.sleep(6);
    }
}
