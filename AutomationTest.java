import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class AutomationTest {
    private KeywordExecutor executor;
    private ExtentReports extent;
    private ExtentTest test;

    @Before
    public void setUp() {
        executor = new KeywordExecutor();
        
        // Initialize ExtentReports
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("test-output/ExtentReport.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

    @Test
    public void testLogin() {
        test = extent.createTest("testLogin");
        
        try {
            test.info("Opening browser");
            executor.execute("OPEN_BROWSER");
            test.pass("Browser opened successfully");

            test.info("Entering username");
            executor.execute("ENTER_TEXT", "usernameField", "myUsername");
            test.pass("Username entered successfully");

            test.info("Entering password");
            executor.execute("ENTER_TEXT", "passwordField", "myPassword");
            test.pass("Password entered successfully");

            test.info("Clicking login button");
            executor.execute("CLICK_BUTTON", "loginButton");
            test.pass("Login button clicked successfully");

            // Add assertions to validate the outcome
            test.pass("Test completed successfully");
        } catch (Exception e) {
            test.fail("Test failed: " + e.getMessage());
        }
    }

    @After
    public void tearDown() {
        // Close the browser or cleanup resources
        executor.close(); // Implement cleanup in your KeywordExecutor
        extent.flush(); // Write everything to the report
    }
}
