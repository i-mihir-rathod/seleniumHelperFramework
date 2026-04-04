package pageObjectsModels;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPageObject extends BasePageObject {

    // ============ Locators  ============
    private final By returningCustomerTitle = By.xpath("//form[@method = 'post']/preceding-sibling::h2");

    // ============ Constructor  ============
    public LoginPageObject(WebDriver driver) {
        super(driver);
    }

    // ============ Actions  ============
    public String getReturningCustomerTitle() {
        return wait.waitUntilElementToBeVisible(returningCustomerTitle).getText();
    }
}
