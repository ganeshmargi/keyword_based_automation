import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class ReportManager {
    private static ExtentReports extent;
    private static ExtentTest test;

    public static void initReport() {
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("extentReport.html");

        // Custom CSS for the popup
        String customCSS = 
            "body { font-family: Arial, sans-serif; } " +
            "#popup { display: none; position: fixed; left: 0; top: 0; width: 100%; height: 100%; " +
            "background-color: rgba(0, 0, 0, 0.5); justify-content: center; align-items: center; } " +
            "#popup-content { background-color: white; padding: 20px; border-radius: 5px; } " +
            ".close-btn { background-color: red; color: white; border: none; padding: 5px 10px; cursor: pointer; }";
        
        // JavaScript for the popup functionality
        String customJavaScript = 
            "function showPopup(content) { " +
            "   document.getElementById('popup-content').innerHTML = content; " +
            "   document.getElementById('popup').style.display = 'flex'; " +
            "} " +
            "function closePopup() { " +
            "   document.getElementById('popup').style.display = 'none'; " +
            "}";

        htmlReporter.config().setCSS(customCSS);
        htmlReporter.config().setJS(customJavaScript);
        
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);

        // Add popup HTML
        String popupHtml = 
            "<div id='popup'>" +
            "   <div id='popup-content'>" +
            "       <button class='close-btn' onclick='closePopup()'>Close</button>" +
            "   </div>" +
            "</div>";
        extent.setSystemInfo("popupHtml", popupHtml);
    }

    public static void startTest(String testName) {
        test = extent.createTest(testName);
    }

    public static void logInfo(String message) {
        test.info("<a href='javascript:void(0);' onclick=\"showPopup('Info: " + message + "')\">Info</a>");
    }

    public static void logPass(String message) {
        test.pass("<a href='javascript:void(0);' onclick=\"showPopup('Pass: " + message + "')\">Pass</a>");
    }

    public static void logFail(String message) {
        test.fail("<a href='javascript:void(0);' onclick=\"showPopup('Fail: " + message + "')\">Fail</a>");
    }

    public static void endReport() {
        extent.flush();
    }

    public static void main(String[] args) {
        initReport();
        startTest("Sample Test");

        logInfo("This is an informational message.");
        logPass("This test passed successfully!");
        logFail("This test failed due to an error!");

        endReport();
    }
}
