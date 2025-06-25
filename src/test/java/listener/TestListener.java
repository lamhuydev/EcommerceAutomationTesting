package listener;

import com.aventstack.extentreports.Status;
import helpers.CaptureHelper;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import reports.AllureManager;
//import reports.ExtentReportManager;
//import reports.ExtentTestManager;
import utils.EmailUtils;
import utils.LogUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestListener implements ITestListener {

    public static int total_test;
    public static int total_test_passed;
    public static int total_test_failed;
    public static int total_test_skipped;

    private static String nameClass;
    public static List<ITestResult> failedTests = new ArrayList<>();

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

        String timestamp = dateFormat.format(new Date());
        result.setAttribute("videoTimestamp", timestamp);
        String testClassName = result.getAllTestMethods()[0].getRealClass().getSimpleName();
        CaptureHelper.startRecord(testClassName);
        nameClass = testClassName;

        LogUtils.info("⚙️ Bắt đầu test suite cho class: " + testClassName);
//        CaptureHelper.startRecord("DemoTestListener");

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
        failedTests.add(result);

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
    public void onFinish(ITestContext context) {
        LogUtils.info("⭐️ Total test: " + total_test);
        LogUtils.info("⭐️ Passed: " + total_test_passed);
        LogUtils.info("⭐️ Failed: " + total_test_failed);
        LogUtils.info("⭐️ Skipped: " + total_test_skipped);

        CaptureHelper.stopRecord();
    }


    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm");

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

}