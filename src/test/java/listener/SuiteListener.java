package listener;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestResult;
import utils.EmailUtils;
import utils.LogUtils;

import java.util.List;
import java.util.ArrayList;
import java.io.File;

public class SuiteListener implements ISuiteListener {

    @Override
    public void onStart(ISuite suite) {
        LogUtils.info("🔵 Bắt đầu chạy toàn bộ suite: " + suite.getName());
    }

    @Override
    public void onFinish(ISuite suite) {
        LogUtils.info("🟢 Kết thúc toàn bộ suite: " + suite.getName());

        // Lấy kết quả tổng hợp từ TestListener (giả sử bạn có biến static dùng chung)
        int totalTest = TestListener.total_test;
        int totalPassed = TestListener.total_test_passed;
        int totalFailed = TestListener.total_test_failed;
        int totalSkipped = TestListener.total_test_skipped;
        List<ITestResult> failedTests = TestListener.failedTests;

        StringBuilder body = new StringBuilder();

        body.append("<div style='font-family:sans-serif;padding:20px;background:#f5f7fa;'>");
        body.append("<div style='max-width:600px;margin:auto;background:#fff;padding:20px;border-radius:8px;border:1px solid #ddd;'>");

        body.append("<h2 style='color:#2c3e50;'>📊 Test Summary</h2>");
        body.append("<table style='width:100%;border-collapse:collapse;font-size:14px;'>");
        body.append("<tr><td><b>Total tests:</b></td><td style='color:#2980b9;'>" + totalTest + "</td></tr>");
        body.append("<tr><td><b style='color:green;'>✅ Passed:</b></td><td style='color:green;'>" + totalPassed + "</td></tr>");
        body.append("<tr><td><b style='color:red;'>❌ Failed:</b></td><td style='color:red;'>" + totalFailed + "</td></tr>");
        body.append("<tr><td><b style='color:orange;'>⏭ Skipped:</b></td><td style='color:orange;'>" + totalSkipped + "</td></tr>");
        body.append("</table>");

        if (!failedTests.isEmpty()) {
            body.append("<hr style='border:none;border-top:1px solid #eee;margin:20px 0;'/>");
            body.append("<h3 style='color:#c0392b;'>📉 Chi tiết Test Case thất bại:</h3>");

            for (ITestResult r : failedTests) {
                String methodName = r.getMethod().getMethodName();
                body.append("<div style='background:#fce4e4;padding:12px;margin-bottom:15px;border-radius:6px;'>");
                body.append("<p style='margin:0;'><b>❗ " + methodName + "</b></p>");
                body.append("</div>");
            }
        }

        body.append("</div></div>");

        // Đính kèm file (tùy chọn – bạn có thể lấy từ `TestListener`)
        List<File> attachments = new ArrayList<>();
        EmailUtils.sendHtmlEmail("📧 Tổng kết Test Suite", body.toString(), attachments);
    }
}
