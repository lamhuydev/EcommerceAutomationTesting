package EcommerceCMS.pages.user;

import keywords.WebUI;
import org.openqa.selenium.By;
import org.testng.Assert;
import reports.AllureManager;
import utils.LogUtils;

public class UserManageProfilePage {
    private By menuManageProfile = By.xpath("(//span[normalize-space()='Manage Profile'])[1]");
    private By headerManageProfile = By.xpath("//h1[normalize-space()='Manage Profile']");

    private By inputName = By.xpath("//input[@placeholder='Your name' and @name='name']");
    private By inputPhone = By.xpath("//input[@placeholder='Your Phone' and @name='phone']");
    private By buttonUpdateProfile = By.xpath("//button[normalize-space()='Update Profile']");

    private By messageUpdateSuccess = By.xpath("//span[contains(normalize-space(), 'updated successfully')]");

    private String nameProfile;

    public void clickMenuProfilePage() {
        if (WebUI.isDisplayed(menuManageProfile)) {
            WebUI.scrollToElement(menuManageProfile);
            WebUI.clickElement(menuManageProfile);
        } else {
            LogUtils.error("clickMenuProfilePage: click menu profile page got error");
            AllureManager.saveTextLog("clickMenuProfilePage: click menu profile page got error");
        }
    }


    public void verifyMenuProfilePage() {
        Assert.assertTrue(WebUI.getCurrentURL().contains("profile"), "verifyMenuProfilePage: is not profile page (URl is not correct)");
        Assert.assertEquals(WebUI.findElement(headerManageProfile).getText(), "Manage Profile", "verifyMenuProfilePage: is not profile page (header profile page not match)");
        Assert.assertTrue(WebUI.isDisplayed(buttonUpdateProfile), "verifyMenuProfilePage: is not profile page (not have button update profile)");
    }

    public void clickButtonUpdateProfile() {
        if (WebUI.isDisplayed(buttonUpdateProfile)) {
            WebUI.scrollToElement(buttonUpdateProfile);
            WebUI.clickElement(buttonUpdateProfile);
        } else {
            LogUtils.error("clickButtonUpdateProfile: click button update profile fail (button update is not displayed)");
            AllureManager.saveTextLog("clickButtonUpdateProfile: click button update profile fail (button update is not displayed)");
        }

    }

    public void verifyUpdateUserProfileSuccess(String expectedValue, String updateType) {
        try {
            if (!WebUI.isDisplayed(inputName)) {
                LogUtils.error("Input field is not visible!");
                return;
            }

            if (updateType != "delete") {
                String currentNameProfile = WebUI.getElementAttribute(inputName, "value");

                Assert.assertTrue(currentNameProfile.contains(expectedValue),
                        "verifyUpdateUserProfileSuccess: update fail (name profile not updated correctly)");

                Assert.assertTrue(WebUI.isDisplayed(messageUpdateSuccess), "Update failed: success message not displayed!");

                LogUtils.info("verifyUpdateUserProfileSuccess: profile updated successfully!");
            }

            Assert.assertTrue(WebUI.isDisplayed(messageUpdateSuccess), "Update failed: success message not displayed!");

            LogUtils.info("verifyUpdateUserProfileSuccess: profile updated successfully!");

        } catch (Exception e) {
            LogUtils.error("Update failed: " + e.getMessage());
        }
    }

    public void updateUserNameProfile(String oldValue, String newValue, String updateType) {
        if (!WebUI.isDisplayed(inputName)) {
            LogUtils.error("updateUserProfile: input field is not displayed!");
            return;
        }

        String currentNameProfile = WebUI.getElementAttribute(inputName, "value");

        switch (updateType.toLowerCase()) {
            case "add":
                newValue = currentNameProfile + " " + newValue;
                WebUI.clearText(inputName);
                WebUI.setText(inputName, newValue);
                break;
            case "replace":
                String updatedNameProfile = currentNameProfile.replace(oldValue, newValue);
                WebUI.clearText(inputName);
                WebUI.setText(inputName, updatedNameProfile);
                break;
            case "delete":
                if (currentNameProfile.contains(oldValue)) {
                    newValue = currentNameProfile.replace(oldValue, "").trim();
                    WebUI.clearText(inputName);
                    WebUI.setText(inputName, newValue);
                } else {
                    LogUtils.warn("updateUserProfile: value to delete not found!");
                    return;
                }
                break;
            default:
                LogUtils.warn("Invalid update type!");
                return;
        }

        clickButtonUpdateProfile();
    }
}
