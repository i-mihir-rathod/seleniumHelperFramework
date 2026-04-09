import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.DriverFactory;

import java.io.File;
import java.io.IOException;

public class Base {
    protected WebDriver driver;

    @BeforeMethod
    public void beforeMethod() {
        driver = DriverFactory.getDriver("chrome");
        driver.get("https://naveenautomationlabs.com/opencart/");
    }

    @AfterMethod
    public void afterMethod(ITestResult result) throws IOException {
        if (ITestResult.FAILURE == result.getStatus()) {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File src = ts.getScreenshotAs(OutputType.FILE);
            File destination = new File("./Screenshots/" + result.getName() + "_" + System.currentTimeMillis() + ".png");
            FileUtils.copyFile(src, destination);
        }
        driver.quit();
    }
}
