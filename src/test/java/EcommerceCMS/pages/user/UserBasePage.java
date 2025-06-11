package EcommerceCMS.pages.user;

import keywords.WebUI;
import org.openqa.selenium.By;

public class UserBasePage {
    private By loginButtonHomePage = By.xpath("(//a[normalize-space()='Login'])[1]");
    private By headerPopup = By.xpath("//b[normalize-space()='Website Demo']");
    private By buttonClosePopup = By.xpath("//b[normalize-space()='Website Demo']/following::button[@data-value='removed']");
    private By buttonOK = By.xpath("//button[normalize-space()='Ok. I Understood']");


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
}
