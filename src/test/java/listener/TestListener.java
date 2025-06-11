package listener;

import com.aventstack.extentreports.Status;
import helpers.CaptureHelper;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import reports.AllureManager;
//import reports.ExtentReportManager;
//import reports.ExtentTestManager;
import utils.LogUtils;

public class TestListener implements ITestListener {

    private static int total_test;
    private static int total_test_passed;
    private static int total_test_failed;
    private static int total_test_skipped;


    // hàm lấy tên Test case từ @Test
    public String getTestName(ITestResult result) {
        return result.getTestName() != null ? result.getTestName() : result.getMethod().getConstructorOrMethod().getName();
    }

    // hàm lấy description từ @Test
    public String getTestDescription(ITestResult result) {
        return result.getMethod().getDescription() != null ? result.getMethod().getDescription() : getTestName(result);
    }


    @Override
    public void onStart(ITestContext result) {
        LogUtils.info("⚙\\uFE0F Thời gian chạy toàn bộ test:" + result.getStartDate());
        CaptureHelper.startRecord("DemoTestListener");

        // Khai báo Properties Config
    }

    @Override
    public void onTestStart(ITestResult result) {
        LogUtils.info("Bắt đầu chạy test: " + result.getName());
        total_test++;

        //Bắt đầu ghi 1 TCs mới vào Extent Report
        //ExtentTestManager.saveToReport(getTestName(result), getTestDescription(result));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        LogUtils.info("✅ Đây là Test case chạy thành công: " + result.getName());
        total_test_passed++;

        //Extent Report
        //ExtentTestManager.logMessage(Status.PASS, "✅ Test case " + result.getName() + " is passed.");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        LogUtils.error("❌ Đây là Test case chạy thất bại: " + result.getName());
        LogUtils.error(result.getThrowable());

        //Extent Report
        //ExtentTestManager.logMessage(Status.FAIL, result.getThrowable().toString());
        //ExtentTestManager.addScreenshot(result.getName());
        //ExtentTestManager.logMessage(Status.FAIL, "❌ Test case " + result.getName() + " is failed.");

        //Allure Report
        AllureManager.saveTextLog(result.getName() + "is failed");
        AllureManager.saveScreenshotPNG();

        total_test_failed++;

        CaptureHelper.captureScreenshot(result.getName());

        // Tạo ticket Jira
        // Gửi hình chụp và logs lên Slack/Telegram/Microsoft Team
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        LogUtils.warn("\uD83D\uDD25 Đây là Test case bị bỏ qua: " + result.getName());

        //Extent Report
        //ExtentTestManager.logMessage(Status.SKIP, "\uD83D\uDD25 Test case " + result.getName() + " is skipped.");
        //ExtentTestManager.logMessage(Status.SKIP, result.getThrowable().toString());


        total_test_skipped++;

        // Tạo ticket Jira
        // Gửi hình chụp và logs lên Slack/Telegram/Microsoft Team
    }


    @Override
    public void onFinish(ITestContext result) {
        LogUtils.info("⭐\\uFE0F Total test:" + total_test);
        LogUtils.info("⭐\\uFE0F Total test passed:" + total_test_passed);
        LogUtils.info("⭐\\uFE0F Total test failed:" + total_test_failed);
        LogUtils.info("⭐\\uFE0F Total test skipped:" + total_test_skipped);

        CaptureHelper.stopRecord();

        // Gửi mail (đính kèm file logs, file report)
        // Có thể kết nối database để lấy data test
        // Call API bên thứ 3
        // Load data từ file excel
        // Xuất Report
        //Kết thúc và thực thi Extents Report
        //ExtentReportManager.getExtentReports().flush();
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

}