import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class CustomTestListener implements ITestListener {
    
    private boolean hasFailed = false;

    @Override
    public void onTestFailure(ITestResult result) {
        hasFailed = true; // Set flag when a test fails
    }

    @Override
    public void onStart(ITestContext context) {
        // Reset the flag when the test context starts
        hasFailed = false;
    }

    public boolean hasFailed() {
        return hasFailed;
    }
}
