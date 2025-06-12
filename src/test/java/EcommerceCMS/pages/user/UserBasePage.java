package EcommerceCMS.pages.user;

import keywords.WebUI;
import org.openqa.selenium.By;
import utils.LogUtils;

public class UserBasePage {
    private By loginButtonHomePage = By.xpath("(//a[normalize-space()='Login'])[1]");
    private By headerPopup = By.xpath("//b[normalize-space()='Website Demo']");
    private By buttonClosePopup = By.xpath("//b[normalize-space()='Website Demo']/following::button[@data-value='removed']");
    private By buttonOK = By.xpath("//button[normalize-space()='Ok. I Understood']");

    private static By inputSearchProduct = By.xpath("//input[@id='search' and @name='keyword']");
    private By buttonSearchProduct = By.xpath("//input[@id='search' and @name='keyword']/following-sibling::div/button[@type='submit']");

    private static By productAfterSearch = By.xpath("(//div[normalize-space()='Products']/following-sibling::ul/li)[1]");


    public void clickButtonLoginHomePage(){
        WebUI.clickElement(loginButtonHomePage);
    }

    public void clickClosePopup(){
        if(WebUI.isDisplayed(headerPopup)){
            WebUI.clickElement(buttonClosePopup);
        }
    }

    public void clickClosePolicy(){
        if (WebUI.isDisplayed(buttonOK)) {
            WebUI.clickElement(buttonOK);
        }
    }

    public static void userSearchProduct(String product){
        WebUI.clearText(inputSearchProduct);
        WebUI.setText(inputSearchProduct, product);
    }

    public void clickButtonSearchProduct(){
        WebUI.clickElement(buttonSearchProduct);
    }

    public static void clickProductAfterSearch(){
        try{
            WebUI.clickElement(productAfterSearch);
        } catch (Exception e){
            LogUtils.error("Can not click to product: " + e.getMessage());
        }

    }
}
