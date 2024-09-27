import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class KeywordExecutor {
    private WebDriver driver;

    public void execute(String keyword, String... params) {
        switch (keyword.toUpperCase()) {
            case "OPEN_BROWSER":
                openBrowser();
                break;
            case "ENTER_TEXT":
                enterText(params[0], params[1]);
                break;
            case "CLICK_BUTTON":
                clickButton(params[0]);
                break;
            // Add more keywords as needed
            default:
                throw new IllegalArgumentException("Invalid keyword: " + keyword);
        }
    }

    private void openBrowser() {
        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    private void enterText(String locator, String text) {
        driver.findElement(By.id(locator)).sendKeys(text);
    }

    private void clickButton(String locator) {
        driver.findElement(By.id(locator)).click();
    }

    public void closeBrowser() {
        if (driver != null) {
            driver.quit();
        }
    }
}
