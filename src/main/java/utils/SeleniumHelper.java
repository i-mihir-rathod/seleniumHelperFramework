package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.util.List;
import java.util.Objects;

public class SeleniumHelper {
    protected WebDriver driver;
    protected WaitUtils wait;
    protected JavascriptHelper js;

    public SeleniumHelper(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitUtils(driver);
        this.js = new JavascriptHelper(driver);
    }

    public WebElement scrollToElements(By element) {
        return js.scrollToElementIfNotVisible(wait.waitUntilElementToBeClickable(element));
    }

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
        js.scrollToElementIfNotVisible(element);
        element.clear();
        element.sendKeys(text);
    }

    public String getText(By by) {
        var element = wait.waitUntilElementToBeVisible(by);
        js.scrollToElementIfNotVisible(element);
        return element.getText();
    }

    public List<String> getTextFromList(By by) {
        return wait.waitUntilElementsToBeVisible(by).stream().map(WebElement::getText).toList();
    }

    public String getPageTitle() throws InterruptedException {
        wait.waitForPageContentLoaded();
        return driver.getTitle();
    }

    public void clickOnElement(By by) {
        var element = wait.waitUntilElementToBeClickable(by);
        js.scrollToElementIfNotVisible(element);
        element.click();
    }

    public void uploadFile(By by, String fileName) {
        var file = FileHelper.getUploadFilePath(fileName);
        var element = wait.waitForElementPresenceInDOM(by);
        js.scrollToElementIfNotVisible(element);
        if (Objects.equals(element.getDomAttribute("type"), "input")) {
            element.sendKeys(file);
        } else {
            js.uploadFile(element, file, false);
        }
    }
}
