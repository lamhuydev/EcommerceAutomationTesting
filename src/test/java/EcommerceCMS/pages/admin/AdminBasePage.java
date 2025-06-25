package EcommerceCMS.pages.admin;

import io.qameta.allure.Step;
import keywords.WebUI;
import org.openqa.selenium.By;

public class AdminBasePage {

    // element of menu products
    private By buttonMenuProducts = By.xpath("//ul[@id='main-menu']//span[normalize-space()='Products']");
    private By buttonAddNewProduct = By.xpath("//span[normalize-space()='Add New Product']");

    @Step("Click meno product")
    public void clickMenuProducts() {
        WebUI.clickElement(buttonMenuProducts);
    }

    @Step("Click add new product")
    public void clickAddNewProduct(){
        clickMenuProducts();
        WebUI.clickElement(buttonAddNewProduct);
    }

}
