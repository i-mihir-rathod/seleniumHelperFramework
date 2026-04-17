package pageObjectsModels;

import dataObjects.ForgotPasswordDetails;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ForgotPasswordPageObject extends BasePageObject{

    // ============ Locators ============
    private final By forgetPasswordTitle = By.xpath("//h1[text()='Forgot Your Password?']");
    private final By continueBtn = By.xpath("//input[@value = 'Continue']");
    private final By warningMsg = By.xpath("//div[contains(@class, 'alert-danger')]");
    private final By emailInput = By.name("email");
    private final By backBtn = By.partialLinkText("Back");

    // ============ Constructor ============
    public ForgotPasswordPageObject(WebDriver driver) {
        super(driver);
    }

    // ============ Actions ============
    public String getForgotPasswordTitle() {
        return seleniumHelper.getText(forgetPasswordTitle);
    }

    public void clickOnContinueBtn() {
        seleniumHelper.clickOnElement(continueBtn);
    }

    public void clickOnBackBtn() {
        seleniumHelper.clickOnElement(backBtn);
    }

    public String getWarningMsg() {
        return seleniumHelper.getText(warningMsg);
    }

    public boolean isWarningMsgDisplayed() {
        return seleniumHelper.isElementDisplayed(warningMsg);
    }

    public void enterEmail(ForgotPasswordDetails forgotPasswordDetails) {
        seleniumHelper.enterText(emailInput, forgotPasswordDetails.getEmail());
    }
}
