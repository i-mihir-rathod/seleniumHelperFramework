package pageObjectsModels;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPageObject extends BasePageObject {

    // ============ Locators  ============
    private final By returningCustomerTitle = By.xpath("//h2[text() = 'Returning Customer']");

    // ============ Constructor  ============
    public LoginPageObject(WebDriver driver) {
        super(driver);
    }

    // ============ Actions  ============
    public String getReturningCustomerTitle() {
        seleniumHelper.isElementDisplayed(returningCustomerTitle);
        return seleniumHelper.getText(returningCustomerTitle);
    }
}
