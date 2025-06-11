package dataproviders;

import helpers.ExcelHelper;
import helpers.SystemHelper;
import org.testng.annotations.DataProvider;
import utils.LogUtils;

public class DataProviderFactory {

    @DataProvider(name = "data_login_success_from_excel")
    public Object[][] dataLoginSuccesFromExcel(){
        ExcelHelper excelHelper = new ExcelHelper();
        Object[][] data = excelHelper.getExcelDataProvider(SystemHelper.getCurrentDir() + "src/test/resources/datatest/UserLogin.xlsx", "Login");

        // Chỉ lấy dòng số 2 (index = 1)
        if (data.length > 1) {
            LogUtils.info("Login Data from Excel: "+ data);
            return new Object[][] { data[0] };
        } else {
            return new Object[][] {}; // Trả về mảng rỗng nếu dữ liệu không đủ
        }
    }

    @DataProvider(name = "data_login_fail_with_invalid_email_from_excel")
    public Object[][] dataLoginFailWithInvalidEmailFromExcel(){
        ExcelHelper excelHelper = new ExcelHelper();
        Object[][] data = excelHelper.getExcelDataProvider(SystemHelper.getCurrentDir() + "src/test/resources/datatest/UserLogin.xlsx", "Login");

        // Chỉ lấy dòng số 2 (index = 1)
        if (data.length > 1) {
            LogUtils.info("Login Data from Excel: "+ data);
            return new Object[][] { data[1] };
        } else {
            return new Object[][] {}; // Trả về mảng rỗng nếu dữ liệu không đủ
        }
    }

    @DataProvider(name = "data_login_fail_with_invalid_password_from_excel")
    public Object[][] dataLoginFailWithInvalidPasswordFromExcel(){
        ExcelHelper excelHelper = new ExcelHelper();
        Object[][] data = excelHelper.getExcelDataProvider(SystemHelper.getCurrentDir() + "src/test/resources/datatest/UserLogin.xlsx", "Login");

        // Chỉ lấy dòng số 2 (index = 1)
        if (data.length > 1) {
            LogUtils.info("Login Data from Excel: "+ data);
            return new Object[][] { data[2] };
        } else {
            return new Object[][] {}; // Trả về mảng rỗng nếu dữ liệu không đủ
        }
    }

}
