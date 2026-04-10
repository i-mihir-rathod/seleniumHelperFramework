import io.github.cdimascio.dotenv.Dotenv;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import utils.DriverFactory;
import utils.FilePathHelper;

import java.io.File;
import java.io.IOException;

public class Base {
    protected WebDriver driver;
    protected static String  baseUrl;
    protected static String browserName;

    @BeforeSuite
    public void beforeSuite() {
        Dotenv dotenv = Dotenv.load();
        baseUrl = dotenv.get("BASE_URL");
        browserName = dotenv.get("BROWSER");
    }

    @BeforeMethod
    public void beforeMethod() {
        driver = DriverFactory.getDriver(browserName);
        driver.get(baseUrl);
    }

    @AfterMethod
    public void afterMethod(ITestResult result) throws IOException {
        if (ITestResult.FAILURE == result.getStatus()) {
            TakesScreenshot ts = (TakesScreenshot) driver;
            var src = ts.getScreenshotAs(OutputType.FILE);
            var destination = new File(FilePathHelper.getFailedCaseScreenShotPath(result.getName()));
            FileUtils.copyFile(src, destination);
        }
        driver.quit();
    }
}
