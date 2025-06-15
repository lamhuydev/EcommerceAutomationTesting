package EcommerceCMS.pages.user;

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


    public void clickButtonLoginHomePage(){
        WebUI.clickElement(loginButtonHomePage);
    }

    public static void clickClosePopup(){
        if(WebUI.isDisplayed(headerPopup)){
            WebUI.clickElement(buttonClosePopup);
        }
    }

    public static void clickClosePolicy(){
        if (WebUI.isDisplayed(buttonOK)) {
            WebUI.clickElement(buttonOK);
        }
    }

    public static void userSearchProduct(String product){
        WebUI.clearText(inputSearchProduct);
        WebUI.setText(inputSearchProduct, product);
    }

    public static void userSearchProductWithClickButtonSearch(String product){
        WebUI.clearText(inputSearchProduct);
        WebUI.setText(inputSearchProduct, product);
        clickButtonSearchProduct();
    }

    public static void clickButtonSearchProduct(){
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
