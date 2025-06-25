package EcommerceCMS.pages.user;

import io.qameta.allure.Step;
import keywords.WebUI;
import org.openqa.selenium.By;
import utils.LogUtils;

public class UserBasePage {
    private static By loginButtonHomePage = By.xpath("(//a[normalize-space()='Login'])[1]");
    private static By headerPopup = By.xpath("//b[normalize-space()='Website Demo']");
    private static By buttonClosePopup = By.xpath("//b[normalize-space()='Website Demo']/following::button[@data-value='removed']");
    private static By buttonOK = By.xpath("//button[normalize-space()='Ok. I Understood']");

    private static By inputSearchProduct = By.xpath("//input[@id='search' and @name='keyword']");
    private static By buttonSearchProduct = By.xpath("//input[@id='search' and @name='keyword']/following-sibling::div/button[@type='submit']");

    private static By productAfterSearch = By.xpath("(//div[normalize-space()='Products']/following-sibling::ul/li)[1]");


    @Step("Click button login in home page")
    public void clickButtonLoginHomePage(){
        WebUI.clickElement(loginButtonHomePage);
    }

    @Step("Click button close popup")
    public static void clickClosePopup(){
        if(WebUI.isDisplayed(headerPopup)){
            WebUI.clickElement(buttonClosePopup);
        }
    }

    @Step("Click button policy")
    public static void clickClosePolicy(){
        if (WebUI.isDisplayed(buttonOK)) {
            WebUI.clickElement(buttonOK);
        }
    }

    @Step("Action search product")
    public static void userSearchProduct(String product){
        WebUI.clearText(inputSearchProduct);
        WebUI.setText(inputSearchProduct, product);
    }

    @Step("Search product with click button search")
    public static void userSearchProductWithClickButtonSearch(String product){
        WebUI.clearText(inputSearchProduct);
        WebUI.setText(inputSearchProduct, product);
        clickButtonSearchProduct();
    }

    @Step("Action click button search")
    public static void clickButtonSearchProduct(){
        WebUI.clickElement(buttonSearchProduct);
    }


    @Step("Action click product in list product after search in search bar")
    public static void clickProductAfterSearch(){
        try{
            WebUI.clickElement(productAfterSearch);
        } catch (Exception e){
            LogUtils.error("Can not click to product: " + e.getMessage());
        }

    }
}
