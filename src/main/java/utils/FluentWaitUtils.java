package utils;

import com.google.common.base.Function;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.NoSuchElementException;

public class FluentWaitUtils {
    protected FluentWait<WebDriver> fw;
    protected JavaScriptHelper js;

    // Constructor
    public FluentWaitUtils(WebDriver driver) {
        this.fw = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(20))          // total wait time
                .pollingEvery(Duration.ofSeconds(2))          // polling every 2 sec
                .ignoring(ElementNotInteractableException.class)
                .ignoring(NoSuchElementException.class);
        this.js = new JavaScriptHelper(driver);
    }

    // Action Methods

    public void clickOnElement(WebElement locator) {
        fw.until((Function<WebDriver, Boolean>) driver1 -> {
            try {
                locator.click();
                return true;
            } catch (ElementNotInteractableException e) {
                // 👉 If not clickable, scroll to element
                js.scrollToElementIfNotInView(locator);
                return false; // retry
            }
        });
    }

}
