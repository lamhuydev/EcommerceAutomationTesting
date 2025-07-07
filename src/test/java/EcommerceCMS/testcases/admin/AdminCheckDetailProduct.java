package EcommerceCMS.testcases.admin;

import Common.BaseTest;
import EcommerceCMS.pages.admin.AdminAddNewProductPage;
import EcommerceCMS.pages.admin.AdminBasePage;
import EcommerceCMS.pages.admin.AdminLoginPage;
import EcommerceCMS.pages.user.UserDetailProductPage;
import dataproviders.DataProviderFactory;
import io.qameta.allure.*;
import keywords.WebUI;
import org.testng.annotations.Test;

public class AdminCheckDetailProduct extends BaseTest {

    AdminBasePage adminBasePage;
    AdminLoginPage adminLoginPage;
    AdminAddNewProductPage adminAddNewProductPage;
    UserDetailProductPage userDetailProductPage;

    @Epic("Admin - Product Management")
    @Feature("Add New Product")
    @Story("Verify new product is correctly created and displayed")
    @Owner("Huy")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Admin adds new product and verify detail is correctly shown on the user product page")
    @Test(dataProvider = "data_add_new_product_form_excel", dataProviderClass = DataProviderFactory.class)
    // flow: login admin > add new product > check detail product
    public void testAdminCheckDetailProduct(String productName, String category, String brand, String unit, String color ,String weight, String tag, String price, String quantity, String description, String img){
        adminBasePage = new AdminBasePage();
        adminLoginPage = new AdminLoginPage();
        adminAddNewProductPage = new AdminAddNewProductPage();
        userDetailProductPage = new UserDetailProductPage();

        // login admin page
        adminLoginPage.loginEcommerceAdminPage();
        WebUI.sleep(2);

        // verify admin page
        adminLoginPage.verifyLoginSuccess();

        // click add new product
        adminBasePage.clickAddNewProduct();

        // verify add new product page
        adminAddNewProductPage.verifyAddNewProductPage();

        // action add new product
        adminAddNewProductPage.addNewProduct(productName, category, brand, unit, color, weight, tag, price, quantity, description, img);

        WebUI.waitForPageLoaded();

        // verify product add new success
        adminAddNewProductPage.verifyAddNewProductSuccess(productName);

        // click view product
        String hrefExpected = adminAddNewProductPage.clickViewProduct();

        // verify detail product page
        userDetailProductPage.verifyProductDetailPage(hrefExpected);

        // verify info product detail with excel data
        userDetailProductPage.verifyInfoProductDetail(productName, unit, price, quantity, description);
        WebUI.sleep(3);
    }
}
