package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Objects;

public class SeleniumHelper {
    protected WebDriver driver;
    protected WaitUtils wait;
    protected JavaScriptHelper js;
    protected FluentWaitUtils fw;


    public SeleniumHelper(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitUtils(driver);
        this.js = new JavaScriptHelper(driver);
        this.fw = new FluentWaitUtils(driver);
    }

//    public WebElement scrollToElements(By element) {
//        return js.scrollToElementIfNotInView(wait.waitUntilElementToBeClickable(element));
//    }

    public boolean isElementDisplayed(By by) {
        WebElement element = null;
        try {
            element = wait.waitUntilElementToBeVisible(by);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return element != null;
    }

    public void enterText(By by, String text) {
        var element = wait.waitUntilElementToBeVisible(by);
        js.scrollToElementIfNotInView(element);
        element.clear();
        element.sendKeys(text);
    }

    public String getText(By by) {
        var element = wait.waitUntilElementToBeVisible(by);
        js.scrollToElementIfNotInView(element);
        return element.getText().trim();
    }

    public List<String> getTextFromList(By by) {
        return wait.waitUntilElementsToBeVisible(by).stream().map(WebElement::getText).toList();
    }

    public String getPageTitle() throws InterruptedException {
        wait.waitForPageContentLoaded();
        return driver.getTitle();
    }

    public void clickUsingFluent(By by) {
        var element = wait.waitUntilElementToBeClickable(by);
        fw.clickOnElement(element);
    }

    public void clickOnElement(By by) {
        var element = wait.waitUntilElementToBeClickable(by);
        js.scrollToElementIfNotInView(element);
        element.click();
    }

    public void uploadFile(By by, String fileName) {
        var file = FilePathHelper.getUploadFilePath(fileName);
        var element = wait.waitForElementPresenceInDOM(by);
        js.scrollToElementIfNotInView(element);

        try {
            element.sendKeys(file);
        } catch (Exception e) {
            js.uploadFile(element, file, false);
        }
    }

    public List<String> getAttributeFromList(By by, String attribute) {
        return wait.waitUntilElementsToBeVisible(by).stream().map(e -> e.getAttribute(attribute)).toList();
    }
}
