package EcommerceCMS.pages.admin;

import EcommerceCMS.pages.user.UserBasePage;
import drivers.DriverManager;
import helpers.PropertiesHelper;
import io.qameta.allure.Step;
import keywords.WebUI;
import org.openqa.selenium.By;
import org.testng.Assert;

public class AdminLoginPage {

    private By headerLoginPage = By.xpath("//h1[contains(text(), 'Login')]");
    private By inputEmail = By.xpath("//input[@id='email']");
    private By inputPassword = By.xpath("//input[@id='password']");
    private By buttonLoginLoginPage = By.xpath("//button[normalize-space()='Login']");
    private By forgotPassword = By.xpath("//a[contains(normalize-space(), 'Forgot password')]");

    private By headerDashboardPage = By.xpath("//h1[normalize-space()='Dashboard']");
    private By menuDashboard = By.xpath("(//span[normalize-space()='Dashboard'])[1]");
    private By adminName = By.xpath("//span[normalize-space()='Admin Example']");
    private By buttonClearCache = By.xpath("//span[normalize-space()='Clear Cache']");


    @Step("Action set text email for admin")
    private void setTextEmail(String email){
        WebUI.clearText(inputEmail);
        WebUI.setText(inputEmail, email);
    }

    @Step("Action set text password for admin")
    private void setTextPassword(String password){
        WebUI.clearText(inputPassword);
        WebUI.setText(inputPassword, password);
    }

    @Step("Action click button login")
    private void clickLoginButton(){
        WebUI.clickElement(buttonLoginLoginPage);
    }


    // login admin Ecommerce with email and password correct for another page
    @Step("Action login ecommerce with admin")
    public void loginEcommerceAdminPage(){
        String url = PropertiesHelper.getValue("url_cms_anhtester");
        WebUI.openURL(url);

        UserBasePage userBasePage = new UserBasePage();
        userBasePage.clickClosePopup();

        userBasePage.clickButtonLoginHomePage();

        verifyLoginPage();

        setTextEmail("admin@example.com");
        setTextPassword("123456");
        clickLoginButton();
    }

    @Step("Verify correct login page")
    public void verifyLoginPage(){
        String headerLoginPageText = DriverManager.getDriver().findElement(headerLoginPage).getText();
        Assert.assertTrue(headerLoginPageText.contains("Login"), "verifyLoginPage: navigate incorrect");

        Assert.assertTrue(DriverManager.getDriver().getCurrentUrl().contains("login"), "verifyLoginPage: navigate incorrect");

        Assert.assertTrue(WebUI.isDisplayed(forgotPassword), "verifyLoginPage: navigate incorrect");
    }

    @Step("Verify login success")
    public void verifyLoginSuccess(){
        WebUI.waitForPageLoaded();
        String currentURL = WebUI.getCurrentURL();

        Assert.assertTrue(currentURL.contains("admin"), "Login admin failed: Url admin page incorrect");
        Assert.assertTrue(WebUI.isDisplayed(menuDashboard), "Login admin failed: admin menu dashboard is not display");
        Assert.assertTrue(WebUI.isDisplayed(adminName), "Login admin failed: admin name is not display");
        Assert.assertTrue(WebUI.isDisplayed(buttonClearCache), "Login admin failed: button clear cache is not display");
        Assert.assertEquals(WebUI.getText(buttonClearCache), "Clear Cache", "Login admin failed: text button clear cache is not match");
    }
}
