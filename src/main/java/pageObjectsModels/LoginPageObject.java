package pageObjectsModels;

import dataObjects.LoginDetails;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPageObject extends BasePageObject {

    // ============ Locators ============
    private final By returningCustomerTitle = By.xpath("//h2[text() = 'Returning Customer']");
    private final By emailInput = By.id("input-email");
    private final By passwordInput = By.id("input-password");
    private final By forgotPasswordLink = By.linkText("Forgotten Password");
    private final By loginBtn = By.xpath("//input[@value = 'Login']");
    private final By warningMsg = By.xpath("//div[contains(@class, 'alert-danger')]");
    private final By continueBtn = By.xpath("//a[text() = 'Continue']");

    // ============ Constructor ============
    public LoginPageObject(WebDriver driver) {
        super(driver);
    }

    // ============ Actions  ============
    public String getReturningCustomerTitle() {
        seleniumHelper.isElementDisplayed(returningCustomerTitle);
        return seleniumHelper.getText(returningCustomerTitle);
    }

    public void enterEmail(String email) {
        seleniumHelper.enterText(emailInput, email);
    }

    public void enterPassword(String password) {
        seleniumHelper.enterText(passwordInput, password);
    }

    public void clickOnForgotPasswordLink() {
        seleniumHelper.clickOnElement(forgotPasswordLink);
    }

    public void clickOnLoginBtn() {
        seleniumHelper.clickOnElement(loginBtn);
    }

    public void clickOnContinueBtn() {
        seleniumHelper.clickOnElement(continueBtn);
    }

    public boolean isWarningMessageDisplayed() {
        return seleniumHelper.isElementDisplayed(warningMsg);
    }

    public String getWarningMsgText() {
        return seleniumHelper.getText(warningMsg);
    }

    // ============ Helper Method ============

    public void fillLoginForm(LoginDetails loginDetails) {
        enterEmail(loginDetails.getEmail());
        enterPassword(loginDetails.getPassword());
        clickOnLoginBtn();
    }
}
