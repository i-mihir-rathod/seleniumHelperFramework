package pageObjectsModels;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ForgotPasswordPageObject extends BasePageObject{

    // ============ Locators ============
    private final By forgetPasswordTitle = By.xpath("//h1[text()='Forgot Your Password?']");

    // ============ Constructor ============
    public ForgotPasswordPageObject(WebDriver driver) {
        super(driver);
    }

    // ============ Actions ============
    public String getForgotPasswordTitle() {
        return seleniumHelper.getText(forgetPasswordTitle);
    }
}
