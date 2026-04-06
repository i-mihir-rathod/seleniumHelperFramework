package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class WaitUtils {
    protected WebDriverWait wait;
    protected WebDriver driver;

    public WaitUtils(WebDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.driver = driver;
    }

    public void hardWait(int second) throws InterruptedException {
        Thread.sleep(second * 1000L);
    }

    public WebElement waitUntilElementToBeClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public WebElement waitUntilElementToBeVisible(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public WebElement waitUntilElementToBeVisible(WebElement locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated((By) locator));
    }

    public List<WebElement> waitUntilElementsToBeVisible(By locator) {
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    public WebElement waitForElementPresenceInDOM(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }


    public void waitForPageContentLoaded() throws InterruptedException {
        try {
            String documentReadyState;
            var retries = 5;
            do {
                documentReadyState =
                        ((JavascriptExecutor) driver).executeScript("return document.readyState")
                                .toString();
                hardWait(1);
                retries--;
            } while (!documentReadyState.equals("complete") && retries > 0);
        } catch (Exception _) {
        }

        wait.until(driver -> ((JavascriptExecutor) driver)
                .executeScript("return document.readyState").equals("complete"));
    }
}
