package helpers;

import drivers.DriverManager;
import org.monte.media.Format;
import org.monte.media.Registry;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.io.FileHandler;
import utils.LogUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.monte.media.FormatKeys.*;
import static org.monte.media.VideoFormatKeys.*;

public class CaptureHelper extends ScreenRecorder {

    // Record with Monte Media library
    public static ScreenRecorder screenRecorder;
    public String name;

    //Hàm xây dựng phải có
    public CaptureHelper(GraphicsConfiguration cfg, Rectangle captureArea, Format fileFormat, Format screenFormat, Format mouseFormat, Format audioFormat, File movieFolder, String name) throws IOException, AWTException {
        super(cfg, captureArea, fileFormat, screenFormat, mouseFormat, audioFormat, movieFolder);
        this.name = name;
    }

    //Hàm này bắt buộc để ghi đè custom lại hàm trong thư viên viết sẵn
    @Override
    protected File createMovieFile(Format fileFormat) throws IOException {

        if (!movieFolder.exists()) {
            movieFolder.mkdirs();
        } else if (!movieFolder.isDirectory()) {
            throw new IOException("\"" + movieFolder + "\" is not a directory.");
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
        return new File(movieFolder, name + "-" + dateFormat.format(new Date()) + "." + Registry.getInstance().getExtension(fileFormat));
    }

    // Start record video
    public static void startRecord(String methodName) {
        //Tạo thư mục để lưu file video vào
        File file = new File(SystemHelper.getCurrentDir() + PropertiesHelper.getValue("record_video_path"));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;

        Rectangle captureSize = new Rectangle(0, 0, width, height);

        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        try {
            screenRecorder = new CaptureHelper(gc, captureSize, new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_AVI), new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE, CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE, DepthKey, 24, FrameRateKey, Rational.valueOf(15), QualityKey, 1.0f, KeyFrameIntervalKey, 15 * 60), new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, "black", FrameRateKey, Rational.valueOf(30)), null, file, methodName);
            screenRecorder.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }

    // Stop record video
    public static void stopRecord() {
        try {
            screenRecorder.stop();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void stopRecord(int delayFinishSecond) {
        try {
            screenRecorder.stop();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    //Tạo format ngày giờ để xíu gắn dô cái name của screenshot hoặc record video không bị trùng tên (không bị ghi đè file)
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");

    public static void captureScreenshot(String screenshotName) {
        try {
            // Tạo tham chiếu đối tượng của TakesScreenshot với dirver hiện tại
            TakesScreenshot ts = (TakesScreenshot) DriverManager.getDriver();
            // Gọi hàm getScreenshotAs để chuyển hóa hình ảnh về dạng FILE
            File source = ts.getScreenshotAs(OutputType.FILE);
            //Kiểm tra folder nếu không tồn tại thì tạo folder
            File theDir = new File(SystemHelper.getCurrentDir() + PropertiesHelper.getValue("screenshot_path"));
            if (!theDir.exists()) {
                theDir.mkdirs();
            }
            // Chổ này đặt tên thì truyền biến "screenName" gán cho tên File chụp màn hình
            FileHandler.copy(source, new File(
                    SystemHelper.getCurrentDir()
                            + PropertiesHelper.getValue("screenshot_path")
                            + File.separator
                            + screenshotName
                            + "_" + dateFormat.format(new Date()) + ".png")
            );
            LogUtils.info("Screenshot taken: " + screenshotName);
            LogUtils.info("Screenshot taken current URL: " + DriverManager.getDriver().getCurrentUrl());
        } catch (Exception e) {
            LogUtils.error("Exception while taking screenshot: " + e.getMessage());
        }
    }


    // Chụp full màn hình, có taskbar
    public static void captureFullScreen(String screenshotName) {
        try {
            // Lấy kích thước toàn màn hình
            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());

            // Sử dụng Robot để chụp toàn bộ màn hình
            Robot robot = new Robot();
            BufferedImage screenShot = robot.createScreenCapture(screenRect);

            // Tạo thư mục nếu chưa tồn tại
            File theDir = new File(PropertiesHelper.getValue("screenshot_path"));
            if (!theDir.exists()) {
                theDir.mkdirs();
            }

            // Định dạng tên file ảnh với thời gian
            Date now = new Date();
            String dateTime = now.toString().replaceAll(" ", "_").replaceAll(":", "_");
            String filePath = PropertiesHelper.getValue("screenshot_path") + screenshotName + "_" + dateTime + ".png";

            // Lưu ảnh
            ImageIO.write(screenShot, "PNG", new File(filePath));
            LogUtils.info("Full-screen screenshot saved: " + filePath);

        } catch (Exception e) {
            LogUtils.error("Exception while taking full screenshot(have taskbar): " + e.getMessage());
//            throw new RuntimeException(e);
        }
    }
}