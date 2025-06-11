package EcommerceCMS.pages.user;

import drivers.DriverManager;
import helpers.PropertiesHelper;
import keywords.WebUI;
import org.openqa.selenium.By;
import org.testng.Assert;

public class UserLoginPage {

    private By headerLoginPage = By.xpath("//h1[contains(text(), 'Login')]");
    private By inputEmail = By.xpath("//input[@id='email']");
    private By inputPassword = By.xpath("//input[@id='password']");
    private By buttonLoginLoginPage = By.xpath("//button[normalize-space()='Login']");
    private By forgotPassword = By.xpath("//a[contains(normalize-space(), 'Forgot password')]");

    private By headerDashboardPage = By.xpath("//h1[normalize-space()='Dashboard']");
    private By menuDashboard = By.xpath("(//span[normalize-space()='Dashboard'])[1]");
    private By messageLoginFail = By.xpath("//span[contains(normalize-space(), 'Invalid login')]");

    private void setTextEmail(String email){
        WebUI.clearText(inputEmail);
        WebUI.setText(inputEmail, email);
    }

    private void setTextPassword(String password){
        WebUI.clearText(inputPassword);
        WebUI.setText(inputPassword, password);
    }

    private void clickLoginButton(){
        WebUI.clickElement(buttonLoginLoginPage);
    }


    // login Ecommerce with email and password correct for another page
    public void loginEcommercePage(){
//        PropertiesHelper.loadAllFiles();
        String url = PropertiesHelper.getValue("url_cms_anhtester");
        WebUI.openURL(url);

        UserBasePage userBasePage = new UserBasePage();
        userBasePage.clickClosePopup();
        userBasePage.clickButtonLoginHomePage();

        verifyLoginPage();

        setTextEmail("customer@example.com");
        setTextPassword("123456");
        clickLoginButton();
    }

    public void loginEcommercePage(String email, String password){
//        PropertiesHelper.loadAllFiles();
        String url = PropertiesHelper.getValue("url_cms_anhtester");
        WebUI.openURL(url);

        UserBasePage userBasePage = new UserBasePage();
        userBasePage.clickClosePopup();
        userBasePage.clickButtonLoginHomePage();

        verifyLoginPage();

        setTextEmail(email);
        setTextPassword(password);
        clickLoginButton();
    }

    public void verifyLoginPage(){
        String headerLoginPageText = DriverManager.getDriver().findElement(headerLoginPage).getText();
        Assert.assertTrue(headerLoginPageText.contains("Login"), "verifyLoginPage: navigate incorrect");

        Assert.assertTrue(DriverManager.getDriver().getCurrentUrl().contains("login"), "verifyLoginPage: navigate incorrect");

        Assert.assertTrue(WebUI.isDisplayed(forgotPassword), "verifyLoginPage: navigate incorrect");
    }

    public void verifyLoginSuccess(){
        String expectedURL = "https://cms.anhtester.com/dashboard"; // URL sau khi đăng nhập thành công
        Assert.assertEquals(DriverManager.getDriver().getCurrentUrl(), expectedURL, "Login failed: Incorrect redirect!");
        Assert.assertTrue(WebUI.isDisplayed(menuDashboard), "verifyLoginSuccess: login fail (menuDashboard)");

    }

    public void verifyLoginFail(){
        UserBasePage userBasePage = new UserBasePage();
        userBasePage.clickClosePolicy();

        Assert.assertTrue(DriverManager.getDriver().getCurrentUrl().contains("login"), "verifyLoginSuccess: login fail, still in login page");
        Assert.assertTrue(WebUI.isDisplayed(messageLoginFail), "verifyLoginSuccess: login fail, still in login page");
    }
}
