package EcommerceCMS.pages.user;

import helpers.ExcelHelper;
import keywords.WebUI;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import reports.AllureManager;
import utils.LogUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class UserSetInfoProductToExcel {

    private static By nameProduct = By.xpath("//div[@id='order_details']/following::div[@class='text-left']/h1");
    private static By priceProduct = By.xpath("//div[normalize-space()='Price:']/following-sibling::div/div/strong");
    private static By colorProduct = By.xpath("//div[@class='aiz-radio-inline']/child::label");
    private static By availableQuantityProduct = By.xpath("//span[@id='available-quantity']");
    private static By descriptionProdut = By.xpath("//div[@id='tab_default_1']/descendant::p");
    private static By imgProduct = By.xpath("(//div[@data-fade='true']/descendant::img)[1]");
    private static By discountPrice = By.xpath("//div[normalize-space()='Discount Price:']/following-sibling::div/div/strong");

    public void verifyInfoProductPage(String nameProductSearch) {
        try {
            String currentUrl = WebUI.getCurrentURL();
            WebUI.sleep(1);

            // Trích xuất slug từ URL
            String[] parts = currentUrl.split("/");
            String productSlug = parts[parts.length - 1];

            // Chuẩn hóa dữ liệu
            String formatSlug = productSlug.replace("-", " ").toLowerCase().trim();
            String formatNameProductSearch = nameProductSearch.toLowerCase().trim();
            String getNameProduct = WebUI.findElement(nameProduct).getText().toLowerCase();

            // Tách từ khóa tìm kiếm
            String[] searchWords = formatNameProductSearch.split("\\s+");
            String[] slugWords = formatSlug.split("\\s+");
            String[] arrayNameProduct = getNameProduct.split("\\s+");

            List<String> matchedList = new ArrayList<>();
            int matchCount = 0;

            // Kiểm tra từng từ khóa tìm kiếm có trong URL hoặc tên sản phẩm
            for (String searchWord : searchWords) {
                for (String slugWord : slugWords) {
                    if (slugWord.equals(searchWord)) {
                        matchCount++;
                        matchedList.add(searchWord);
                        break;
                    }
                }
                for (String productWord : arrayNameProduct) {
                    if (productWord.equals(searchWord)) {
                        matchCount++;
                        matchedList.add(searchWord);
                        break;
                    }
                }
            }

            boolean enoughMatches = matchCount >= 2; // Chỉ cần >= 2 từ khớp là pass

            // Hiển thị dữ liệu debug
            LogUtils.info("formatSlug: " + formatSlug);
            LogUtils.info("Product Name: " + getNameProduct);
            LogUtils.info("Search Words: " + Arrays.toString(searchWords));
            LogUtils.info("Matched Words: " + matchedList.toString());
            LogUtils.info("Total Matches: " + matchCount);

            Assert.assertTrue(enoughMatches, "verifyInfoProductPage: Failed (Less than 2 matching words in URL or product name)");

            LogUtils.info("verifyInfoProductPage: Passed - Product is correctly displayed on the page!");

        } catch (NoSuchElementException e) {
            LogUtils.error("verifyInfoProductPage: Element not found - " + e.getMessage());
            Assert.fail("verifyInfoProductPage: Failed due to missing product element");
        } catch (Exception e) {
            LogUtils.error("verifyInfoProductPage: Unexpected failure - " + e.getMessage());
            Assert.fail("verifyInfoProductPage: Unexpected error");
        }
    }


    public static void getInfoProductToExcel() {
        try {
            List<WebElement> getColorProduct = WebUI.findElements(colorProduct);
            List<String> colors = new ArrayList<>();
            for (WebElement label : getColorProduct) {
                colors.add(label.getAttribute("data-title"));
            }
            WebUI.scrollToElement(imgProduct);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            String currentTime = LocalDateTime.now().format(formatter);

            String price;
            if (WebUI.checkElementExist(priceProduct) && WebUI.isDisplayed(priceProduct)) {
                price = WebUI.getText(priceProduct);
            } else if (WebUI.checkElementExist(discountPrice) && WebUI.isDisplayed(discountPrice)) {
                price = WebUI.getText(discountPrice);
            } else {
                price = "N/A";
            }

            // Gọi hàm với danh sách dữ liệu động
            List<String> productData = Arrays.asList(
                    WebUI.getText(nameProduct),
                    price,
                    String.join(", ", colors),
                    WebUI.getText(availableQuantityProduct),
                    WebUI.getText(descriptionProdut),
                    WebUI.getElementAttribute(imgProduct, "src"),
                    currentTime
            );

            ExcelHelper.writeDataToExcel(productData, "src/test/resources/datatest/ProductData.xlsx");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void verifyAddProductDataToExcel() {

        String price;
        if (WebUI.checkElementExist(priceProduct) && WebUI.isDisplayed(priceProduct)) {
            price = WebUI.getText(priceProduct);
        } else if (WebUI.checkElementExist(discountPrice) && WebUI.isDisplayed(discountPrice)) {
            price = WebUI.getText(discountPrice);
        } else {
            price = "N/A";
        }
        List<WebElement> getColorProduct = WebUI.findElements(colorProduct);
        List<String> colors = new ArrayList<>();
        for (WebElement label : getColorProduct) {
            colors.add(label.getAttribute("data-title"));
        }

        // Lấy thời gian hiện tại và định dạng
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String currentTime = LocalDateTime.now().format(formatter);


        // Tạo dữ liệu mong đợi với Color
        Map<String, String> expectedDataProduct = new HashMap<>();
        expectedDataProduct.put("Product Name", WebUI.getText(nameProduct));
        expectedDataProduct.put("Price", price);
        expectedDataProduct.put("Available Quantity", WebUI.getText(availableQuantityProduct));
        expectedDataProduct.put("Description", WebUI.getText(descriptionProdut));
        expectedDataProduct.put("URL image", WebUI.getElementAttribute(imgProduct, "src"));
        expectedDataProduct.put("Color ", String.join(", ", colors));
        expectedDataProduct.put("Time", currentTime);

        ExcelHelper excelHelper = new ExcelHelper();
        Map<String, String> actualData = excelHelper.lastProductEntry("src/test/resources/datatest/ProductData.xlsx");

        LogUtils.info("expectedDataProduct: " + expectedDataProduct.toString());
        LogUtils.info("actualData: " + actualData.toString());


        Assert.assertEquals(actualData, expectedDataProduct, "Dữ liệu sản phẩm trong Excel không khớp!");
    }

}
