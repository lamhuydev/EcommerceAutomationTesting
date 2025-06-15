package EcommerceCMS.testcases.user;

import Common.BaseTest;
import EcommerceCMS.pages.user.UserBasePage;
import EcommerceCMS.pages.user.UserLoginPage;
import EcommerceCMS.pages.user.UserProductDetailPage;
import io.qameta.allure.*;
import keywords.WebUI;
import org.testng.annotations.Test;

public class UserSetInfoProductToExcelTest extends BaseTest {

    private UserLoginPage userLoginPage;
    private UserProductDetailPage userProductDetailPage;

    @Epic("Regression")
    @Feature("Product Management")
    @Story("Save Product Data to Excel")
    @Owner("Huy")
    @Severity(SeverityLevel.NORMAL)
    @Description("Run Flow This Case: Login User Page > Get Random Product Data > Save Data to Excel")
    @Test
    public void testSetInfoProduct(){

        userLoginPage = new UserLoginPage();
        userProductDetailPage = new UserProductDetailPage();
        String nameProductSearch = "Laptop edit";

        userLoginPage.loginEcommercePage();
        userLoginPage.verifyLoginSuccess();

        UserBasePage.userSearchProduct(nameProductSearch);
        WebUI.sleep(2);
        UserBasePage.clickProductAfterSearch();

        userProductDetailPage.verifyInfoProductPage(nameProductSearch);

        userProductDetailPage.getInfoProductToExcel();
        userProductDetailPage.verifyAddProductDataToExcel();
    }

}
