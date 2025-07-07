package EcommerceCMS.pages.admin;

import helpers.SystemHelper;
import io.qameta.allure.Step;
import keywords.WebUI;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import utils.LogUtils;

import java.util.List;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.nio.file.Paths;

public class AdminAddNewProductPage {
    private int countPic = 1;
    private By textHeaderAddNewProductPage = By.xpath("//h5[normalize-space()='Add New Product']");
    private By textProductInformation = By.xpath("//h5[normalize-space()='Product Information']");

    private By inputName = By.xpath("//input[@name='name']");

    private By buttonCategory = By.xpath("//button[@data-id='category_id']");
    private By inputSearchCategory = By.xpath("//button[@data-id='category_id']/following-sibling::div//input");
    private By optionSearchCategory = By.xpath("(//div[@id='bs-select-1']//li)[1]");

    private By buttonBrand = By.xpath("//button[@data-id='brand_id']");
    private By inputSearchBrand = By.xpath("//button[@data-id='brand_id']/following-sibling::div//input");
    private By optionSearchBrand = By.xpath("(//div[@id='bs-select-2']//li)[1]");

    private By inputUnit = By.xpath("//input[@name='unit']");
    private By inputWeight = By.xpath("//input[@name='weight']");
    private By inputTags = By.xpath("//tags[@role='tagslist']");

    private By galleryImage = By.xpath("//label[@for='signinSrEmail' and contains(normalize-space(), 'Gallery Images ')]/following-sibling::div/div");
    private By thumbnailImage = By.xpath("//label[@for='signinSrEmail' and contains(normalize-space(), 'Thumbnail Image ')]/following-sibling::div/div[@data-type='image']");

    // img preview
    private By imagePreviewGallery = By.xpath("(//div[contains(@class, 'file-preview')]/div[contains(@class, 'file-preview-item')])[1]");
    private By imagePreviewThumbnail = By.xpath("(//div[contains(@class, 'file-preview')]/div[contains(@class, 'file-preview-item')])[2]");

    private By buttonEnableColor = By.xpath("//button[@data-id='colors']/parent::div/parent::div/following-sibling::div/label");
    private By buttonSelectColor = By.xpath("//button[@data-id='colors']");
    private By inputSearchColor = By.xpath("//button[@data-id='colors']/following-sibling::div//input");
    private By colorPosition_1 = By.xpath("(//button[@data-id='colors']/following-sibling::div//ul/li)[1]");
    private By titleColor = By.xpath("//h5[normalize-space()='Product Variation']");


    private By inputUnitPrice = By.xpath("//input[@name='unit_price']");
    private By inputQuantity = By.xpath("//input[@name='current_stock']");

    // quantity for color
    private final String xpathQtyPrefix = "//input[@name='qty_";
    // price for color
    private final String xpathPricePrefix = "//input[@name='price_";

    private By divDescription = By.xpath("//div[@role='textbox']");

    private By buttonSaveAndPublish = By.xpath("//button[normalize-space()='Save & Publish']");

    // element of popup upload file
    private By buttonBrowse = By.xpath("//button[normalize-space()='Browse']");
    private By uploadNew = By.xpath("//a[normalize-space()='Upload New']");
    private By buttonAddFile = By.xpath("//button[normalize-space()='Add Files']");
    private By selectFile = By.xpath("//a[normalize-space()='Select File']");
//    private By imageUploaded = By.xpath("(//div[@title='laptop_asus.webp'])[1]")


    // mesage add new product success
    private By messageSuccess = By.xpath("//span[normalize-space()='Product has been inserted successfully']");

    // element of product page
    private By headerProductPage = By.xpath("//h5[normalize-space()='All Product']");
    private By inputSearchProduct = By.xpath("//input[@id='search']");

    // element of product's search
    private By search_nameProduct = By.xpath("(//tbody/tr)[1]/td[2]//span");
    private By notFound = By.xpath("//td[normalize-space()='Nothing found']");
    private By buttonViewProduct = By.xpath("(//a[@title='View'])[1]");

    @Step("Verify correct add new product page")
    public void verifyAddNewProductPage() {
        String currentURL = WebUI.getCurrentURL();
        String textHeader = WebUI.getText(textHeaderAddNewProductPage);

        Assert.assertTrue(WebUI.isDisplayed(textProductInformation), "verify add new product page failed, text add new inform is not display");
        Assert.assertEquals(textHeader, "Add New Product", "verify failed: text header add new product page not match");
        Assert.assertTrue(WebUI.isDisplayed(textHeaderAddNewProductPage), "verify add new product page failed, header is not display");
        Assert.assertTrue(currentURL.contains("create"), "verify add new product page failed, url not correct");

        LogUtils.info("Verify add new product page successfully");
    }

    @Step("Action add new product")
    public void addNewProduct(String productName, String category, String brand, String unit, String color ,  String weight, String tag, String price, String quantity, String description, String img) {

        // clear text and set text input product name
        WebUI.clearText(inputName);
        WebUI.setText(inputName, productName);

        // select category
        WebUI.clickElement(buttonCategory);
        WebUI.setText(inputSearchCategory, category);
        WebUI.clickElement(optionSearchCategory);

        // select brand
        WebUI.clickElement(buttonBrand);
        WebUI.setText(inputSearchBrand, brand);
        WebUI.clickElement(optionSearchBrand);

        // clear text and set text input unit
        WebUI.clearText(inputUnit);
        WebUI.setText(inputUnit, unit);

        // clear text and set text input weight
        WebUI.clearText(inputWeight);
        WebUI.setText(inputWeight, weight);

        // set text input tags
        WebUI.setText(inputTags, tag);

        // click gallery image
        WebUI.clickElement(galleryImage);

        // handle popup upload file
        handlePopupUploadFile(img);

        WebUI.sleep(0.5);
        WebUI.waitForElementVisible(imagePreviewGallery);

        // click thumbnail image
        WebUI.clickElement(thumbnailImage);

        // handle popup upload file
        handlePopupUploadFile(img);

        WebUI.waitForElementVisible(imagePreviewThumbnail);

        // handle select color
//        WebUI.scrollToElementAtTop(buttonSelectColor);
        WebUI.clickElement(buttonEnableColor);
        WebUI.clickElement(buttonSelectColor);
        WebUI.clearText(inputSearchColor);
        WebUI.setText(inputSearchColor, color);
        WebUI.clickElement(colorPosition_1);
        WebUI.clickElement(titleColor);

        WebUI.sleep(1);

        // clear text and set text input price
        WebUI.clearText(inputUnitPrice);
        WebUI.setText(inputUnitPrice, price);

        WebUI.sleep(1);

        // clear text and set text input quantity
        By quantityForColor = By.xpath(xpathQtyPrefix + color + "']");
//        WebUI.scrollToElementAtBottom(quantityForColor);
        WebUI.clearText(quantityForColor);
        WebUI.setText(quantityForColor, quantity);

        // clear text price for color
        By priceForColor = By.xpath(xpathPricePrefix + color + "']");
        WebUI.clearText(priceForColor);
        WebUI.setText(priceForColor, String.valueOf(price));

//        WebUI.scrollToElement(divDescription);
        WebUI.sleep(2);
        WebUI.setText(divDescription, description);

        LogUtils.info("📝 Product Name: " + productName);
        LogUtils.info("📂 Category: " + category);
        LogUtils.info("🏷️ Brand: " + brand);
        LogUtils.info("📏 Unit: " + unit);
        LogUtils.info("⚖️ Weight: " + weight);
        LogUtils.info("🔖 Tag: " + tag);
        LogUtils.info("💵 Price: " + price);
        LogUtils.info("💵 Color: " + color);
        LogUtils.info("📦 Quantity: " + quantity);
        LogUtils.info("🧾 Description: " + description);
        LogUtils.info("🖼️ Image path: " + img);

        clickButtonSaveAndPublish();
    }

    @Step("Handle popup upload file image in add new product")
    public void handlePopupUploadFile(String img) {
        WebUI.sleep(2);

        // Click mở popup chọn file
        WebUI.clickElement(uploadNew);
        WebUI.clickElement(buttonBrowse);
        WebUI.sleep(2); // Đợi popup thật sự hiện ra

        // Xác định đường dẫn file
        String filePath = Paths.get(SystemHelper.getCurrentDir(), "src", "test", "resources", "datatest", img)
                .toAbsolutePath().toString();
        LogUtils.info("📁 File path để upload: " + filePath);

        // Kiểm tra file tồn tại
        File file = new File(filePath);
        if (!file.exists()) {
            LogUtils.error("❌ File không tồn tại: " + filePath);
            throw new RuntimeException("File upload không tồn tại: " + filePath);
        }

        // Tiến hành upload bằng Robot
        try {
            Robot rb = new Robot();
            rb.setAutoDelay(200); // Tăng delay cho an toàn
            rb.waitForIdle();

            // Copy đường dẫn vào clipboard
            StringSelection selection = new StringSelection(filePath);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);
            LogUtils.info("📋 Đã copy đường dẫn file vào clipboard");

            // Dán Ctrl + V
            rb.keyPress(KeyEvent.VK_CONTROL);
            rb.keyPress(KeyEvent.VK_V);
            rb.keyRelease(KeyEvent.VK_V);
            rb.keyRelease(KeyEvent.VK_CONTROL);
            LogUtils.info("⌨️ Đã thực hiện Ctrl + V");

            WebUI.sleep(1); // Đợi nội dung dán vào

            // Nhấn Enter để chọn
            rb.keyPress(KeyEvent.VK_ENTER);
            rb.keyRelease(KeyEvent.VK_ENTER);
            LogUtils.info("⏎ Đã nhấn Enter để xác nhận chọn file");

            WebUI.sleep(2); // Chờ hệ thống xử lý upload

            // Xác nhận trong UI
            clickSelectFileImage();
            WebUI.sleep(1);
            addFileImage(img);
            LogUtils.info("✅ Upload file thành công và đã xác nhận trong UI");

        } catch (AWTException e) {
            LogUtils.error("⚠️ Upload file thất bại: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Step("Click button add image file")
    public void clickButtonAddFile() {
        WebUI.clickElement(buttonAddFile);
    }

    @Step("Click button save and publish product")
    public void clickButtonSaveAndPublish() {
        WebUI.clickElement(buttonSaveAndPublish);
    }

    @Step("Action add file image")
    public void addFileImage(String img) {
        // Đếm số ảnh hiện có khớp với tên file
        List<WebElement> imageElements = WebUI.findElements(By.xpath("//div[@title='" + img + "']"));

        int count = imageElements.size() - 1;

        if (count == 0) {
            LogUtils.warn("⚠️ Không tìm thấy ảnh nào có title: " + img);
            return;
        }

        // Lấy ảnh cuối (vừa upload)
        By fileImg = By.xpath("(//div[@title='" + img + "'])[" + count + "]");

        WebUI.waitForElementVisible(fileImg, 10);
        WebUI.clickElementWithScript(fileImg);
        WebUI.sleep(0.5);

        clickButtonAddFile();
    }

    @Step("Verify add new product success")
    public void verifyAddNewProductSuccess(String nameProduct) {
//        WebUI.waitForPageLoaded();

        // assert message add new success alert and correct text
        Assert.assertEquals(WebUI.getText(messageSuccess), "Product has been inserted successfully", "content of message add new product success is not match");
        Assert.assertTrue(WebUI.isDisplayed(messageSuccess), "message add new product success is not display");

        // assert correct url
        String currentURL = WebUI.getCurrentURL();
        Assert.assertTrue(currentURL.contains("products/admin"), "add new product fail, url is not correct");


        WebUI.searchText(inputSearchProduct, nameProduct);

        String nameProductIntableSearch = WebUI.getText(search_nameProduct);



        Assert.assertTrue(WebUI.isDisplayed(headerProductPage), "add new product fail, header product page is not display");
        Assert.assertEquals(WebUI.getText(headerProductPage), "All Product", "add new product fail, text header product not match");
//        Assert.assertFalse(WebUI.isDisplayed(notFound), "add new product faild, not found product: " + nameProduct);
        Assert.assertEquals(nameProductIntableSearch, nameProduct, "add new product fail, name product not match");

        LogUtils.info("✅ Verify add new product successfully !");
    }

    @Step("Click button view product")
    public String clickViewProduct() {
        String originalWindow = WebUI.getCurrentWindowHandle();
        int currentTabCount = WebUI.getAllWindowHandles().size();

        String href = WebUI.getElementAttribute(buttonViewProduct, "href");
        WebUI.clickElement(buttonViewProduct);

        WebUI.waitForNewTabOpened(currentTabCount); // đợi mở tab mới
        WebUI.switchToNewTab(originalWindow);       // chuyển sang tab mới
        return href;
    }

    @Step("Click tab select file in popup add image product file")
    public void clickSelectFileImage() {
        WebUI.clickElement(selectFile);
    }


}
