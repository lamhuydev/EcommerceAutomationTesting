package EcommerceCMS.testcases.user;

import Common.BaseTest;
import EcommerceCMS.pages.user.UserLoginPage;
import EcommerceCMS.pages.user.UserManageProfilePage;
import io.qameta.allure.*;
import keywords.WebUI;
import org.testng.annotations.Test;

public class UserUpdateProfileTest extends BaseTest {

    UserLoginPage userLoginPage;
    UserManageProfilePage userManageProfilePage;

    @Epic("Regression")
    @Feature("User Profile Management")
    @Story("Update User Profile")
    @Owner("Huy")
    @Severity(SeverityLevel.NORMAL)
    @Test
    @Description("This Test Case Will Add New Name to Profile")
    public void testUpdateUserWithAddNewName() {
        userLoginPage = new UserLoginPage();
        userManageProfilePage = new UserManageProfilePage();

        userLoginPage.loginEcommercePage();
        userLoginPage.verifyLoginSuccess();

        WebUI.sleep(1);
        userManageProfilePage.clickMenuProfilePage();
        userManageProfilePage.verifyMenuProfilePage();

        userManageProfilePage.updateUserNameProfile("", "Hoàng", "add");
        WebUI.sleep(1);
        userManageProfilePage.verifyUpdateUserProfileSuccess("Hoàng", "add");
    }


    @Epic("Regression")
    @Feature("User Profile Management")
    @Story("Change Part of User Profile")
    @Owner("Huy")
    @Severity(SeverityLevel.NORMAL)
    @Test
    @Description("This Test Case Will Change Part of Username")
    public void testUpdateUserWithChangeValue() {
        userLoginPage = new UserLoginPage();
        userManageProfilePage = new UserManageProfilePage();

        userLoginPage.loginEcommercePage();
        userLoginPage.verifyLoginSuccess();

        WebUI.sleep(1);
        userManageProfilePage.clickMenuProfilePage();
        userManageProfilePage.verifyMenuProfilePage();

        userManageProfilePage.updateUserNameProfile("thuủi", "tài","replace");
        WebUI.sleep(1);
        userManageProfilePage.verifyUpdateUserProfileSuccess("tài", "replace");
    }


    @Epic("Regression")
    @Feature("User Profile Management")
    @Story("Delete Part of User Profile")
    @Owner("Huy")
    @Severity(SeverityLevel.NORMAL)
    @Test
    @Description("This Test Case Will Delete Part of Username")
    public void testUpdateUserWithDeletePart() {
        userLoginPage = new UserLoginPage();
        userManageProfilePage = new UserManageProfilePage();

        userLoginPage.loginEcommercePage();
        userLoginPage.verifyLoginSuccess();

        WebUI.sleep(1);
        userManageProfilePage.clickMenuProfilePage();
        userManageProfilePage.verifyMenuProfilePage();

        userManageProfilePage.updateUserNameProfile("Huy", "", "delete");
        WebUI.sleep(1);
        userManageProfilePage.verifyUpdateUserProfileSuccess("", "delete");
    }

}
