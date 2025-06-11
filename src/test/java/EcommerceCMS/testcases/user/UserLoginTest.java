package EcommerceCMS.testcases.user;

import Common.BaseTest;
import EcommerceCMS.pages.user.UserBasePage;
import EcommerceCMS.pages.user.UserLoginPage;
import dataproviders.DataProviderFactory;
import io.qameta.allure.*;
import keywords.WebUI;
import org.testng.annotations.Test;


public class UserLoginTest extends BaseTest {

    UserLoginPage userLoginPage;

    @Epic("Authentication")
    @Feature("User Login")
    @Story("Valid Login")
    @Owner("Huy")
    @Severity(SeverityLevel.CRITICAL)
    @Description("User can log in with correct credentials")
    @Test (priority = 1 ,dataProvider = "data_login_success_from_excel", dataProviderClass = DataProviderFactory.class)
    public void testUserLoginPageSuccess(String email, String password){
        userLoginPage = new UserLoginPage();

        userLoginPage.loginEcommercePage(email, password);
        WebUI.sleep(2);
        userLoginPage.verifyLoginSuccess();
    }

    @Epic("Authentication")
    @Feature("User Login")
    @Story("Valid Login")
    @Owner("Huy")
    @Severity(SeverityLevel.CRITICAL)
    @Description("User can't log in with incorrect credentials")
    @Test (priority = 2 ,dataProvider = "data_login_fail_with_invalid_email_from_excel", dataProviderClass = DataProviderFactory.class)
    public void testUserLoginPageFailWithInvalidEmail(String email, String password){
        userLoginPage = new UserLoginPage();

        userLoginPage.loginEcommercePage(email, password);
        WebUI.sleep(2);
        userLoginPage.verifyLoginFail();
    }

    @Epic("Authentication")
    @Feature("User Login")
    @Story("Valid Login")
    @Owner("Huy")
    @Severity(SeverityLevel.CRITICAL)
    @Description("User can't log in with incorrect credentials")
    @Test (priority = 3 ,dataProvider = "data_login_fail_with_invalid_password_from_excel", dataProviderClass = DataProviderFactory.class)
    public void testUserLoginPageFailWithInvalidPassword(String email, String password){
        userLoginPage = new UserLoginPage();

        userLoginPage.loginEcommercePage(email, password);
        WebUI.sleep(2);
        userLoginPage.verifyLoginFail();
    }




}
