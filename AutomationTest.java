import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AutomationTest {
    private KeywordExecutor executor;
    private ExtentReports extent;
    private ExtentTest test;

    @BeforeClass
    public void setUp() {
        executor = new KeywordExecutor();
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("test-output/ExtentReport.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

    @DataProvider(name = "excelDataProvider")
    public Object[][] excelDataProvider() throws IOException {
        ExcelUtil excelUtil = new ExcelUtil("path/to/your/testcases.xlsx");
        List<String[]> data = excelUtil.readTestData("TestCases"); // Adjust sheet name accordingly

        // Group keywords by Test Case ID
        Map<String, List<String[]>> testCaseMap = new HashMap<>();
        for (String[] row : data) {
            String testCaseId = row[0];
            testCaseMap.putIfAbsent(testCaseId, new ArrayList<>());
            testCaseMap.get(testCaseId).add(row);
        }

        // Convert the map to a 2D array
        List<Object[]> testCases = new ArrayList<>();
        for (Map.Entry<String, List<String[]>> entry : testCaseMap.entrySet()) {
            testCases.add(new Object[]{entry.getKey(), entry.getValue()});
        }
        
        return testCases.toArray(new Object[0][]);
    }

    @Test(dataProvider = "excelDataProvider")
    public void testKeywordDriven(String testCaseId, List<String[]> keywords) {
        test = extent.createTest("Executing Test Case ID: " + testCaseId);
        
        try {
            for (String[] keywordData : keywords) {
                String keyword = keywordData[1];
                String param1 = keywordData[2];
                String param2 = keywordData[3];
                executor.execute(keyword, param1, param2);
                test.info("Executed keyword: " + keyword);
            }
            test.pass("All steps executed successfully for Test Case ID: " + testCaseId);
        } catch (Exception e) {
            test.fail("Execution failed for Test Case ID: " + testCaseId + ": " + e.getMessage());
        }
    }

    @AfterClass
    public void tearDown() {
        executor.closeBrowser();
        extent.flush();
    }
}
