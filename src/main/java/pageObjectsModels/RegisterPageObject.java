package pageObjectsModels;

import dataObjects.RegisterPageDataObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegisterPageObject extends BasePageObject {
    // ============ Locators ============

    private final By registerAccountTitle = By.xpath("//div[@id = 'content']/h1");
    private final By firstNameInput = By.id("input-firstname");
    private final By lastNameInput = By.id("input-lastname");
    private final By emailInput = By.id("input-email");
    private final By telephoneInput = By.id("input-telephone");
    private final By passwordInput = By.id("input-password");
    private final By confirmPasswordInput = By.id("input-confirm");
    private final By continueBtn = By.xpath("//input[@value = 'Continue']");
    private final By privacyPolicyCheckBox = By.xpath("//input[@name = 'agree']");
    private final By subscribeCheckBoxYes = By.xpath("//label[text() = 'Subscribe']/following-sibling::div//input[@value = '1']");
    private final By subscribeCheckBoxNo = By.xpath("//label[text() = 'Subscribe']/following-sibling::div//input[@value = '0']");
    private final By loginPageLink = By.partialLinkText("login page");
    private final By warningMsg = By.xpath("//div[contains(@class, 'alert-danger')]");

    // ============ Dynamic Locators  ============

    private By errorFieldByName(String fieldName) {
        return By.xpath("//label[text() = '" + fieldName + "']/following-sibling::div/div");
    }

    // ============ Constructor ============

    public RegisterPageObject(WebDriver driver) {
        super(driver);
    }

    // ============ Actions Methods ============
    public String getRegisterAccountTitle() {
        return seleniumHelper.getText(registerAccountTitle);
    }

    public void enterFirstName(String fName) {
        seleniumHelper.enterText(firstNameInput, fName);
    }

    public void enterLastName(String lName) {
        seleniumHelper.enterText(lastNameInput, lName);
    }

    public void enterEmail(String mail) {
        seleniumHelper.enterText(emailInput, mail);
    }

    public void enterTelephone(String number) {
        seleniumHelper.enterText(telephoneInput, number);
    }

    public void enterPassword(String pass) {
        seleniumHelper.enterText(passwordInput, pass);
    }

    public void enterConfirmPassword(String cPass) {
        seleniumHelper.enterText(confirmPasswordInput, cPass);
    }

    public void clickOnContinueBtn() {
        seleniumHelper.clickOnElement(continueBtn);
    }

    public void agreePrivacyPolicy() {
        seleniumHelper.clickOnElement(privacyPolicyCheckBox);
    }

    public void subscribeStatus(String status) {
        if (status.equalsIgnoreCase("Yes")) {
            seleniumHelper.clickOnElement(subscribeCheckBoxYes);
        } else {
            seleniumHelper.clickOnElement(subscribeCheckBoxNo);
        }
    }

    public void clickOnLoginPageLink() {
        seleniumHelper.clickOnElement(loginPageLink);
    }

    // ============ Error Messages Actions Methods ============

    public String getWarningMsg() {
        return seleniumHelper.getText(warningMsg);
    }

    public String getFirstNameErrorText() {
        return seleniumHelper.getText(errorFieldByName("First Name"));
    }

    public String getLastNameErrorText() {
        return seleniumHelper.getText(errorFieldByName("Last Name"));
    }

    public String getEmailErrorText() {
        return seleniumHelper.getText(errorFieldByName("E-Mail"));
    }

    public String getTelephoneErrorText() {
        return seleniumHelper.getText(errorFieldByName("Telephone"));
    }

    public String getPasswordErrorText() {
        return seleniumHelper.getText(errorFieldByName("Password"));
    }

    public String getConfirmPasswordErrorText() {
        return seleniumHelper.getText(errorFieldByName("Password Confirm"));
    }

    // ============ Get Displayed Methods ============

    public boolean isWarningMsgDisplayed() {
        return seleniumHelper.isElementDisplayed(warningMsg);
    }

    public boolean isFirstNameErrorMsgDisplayed() {
        return seleniumHelper.isElementDisplayed(errorFieldByName("First Name"));
    }

    public boolean isLastNameErrorMsgDisplayed() {
        return seleniumHelper.isElementDisplayed(errorFieldByName("Last Name"));
    }

    public boolean isEmailErrorMsgDisplayed() {
        return seleniumHelper.isElementDisplayed(errorFieldByName("E-Mail"));
    }

    public boolean isTelephoneErrorMsgDisplayed() {
        return seleniumHelper.isElementDisplayed(errorFieldByName("Telephone"));
    }

    public boolean isPasswordErrorMsgDisplayed() {
        return seleniumHelper.isElementDisplayed(errorFieldByName("Password"));
    }

    public boolean isConfirmPasswordErrorMsgDisplayed() {
        return seleniumHelper.isElementDisplayed(errorFieldByName("Password Confirm"));
    }

    // ============ Helper Methods ============
    public void fillRegisterForm(RegisterPageDataObject registerData) {
        enterFirstName(registerData.getFirstName());
        enterLastName(registerData.getLastName());
        enterEmail(registerData.getEmail());
        enterTelephone(registerData.getTelephone());
        enterPassword(registerData.getPassword());
        enterConfirmPassword(registerData.getConfirmPassword());
        subscribeStatus(registerData.getSubscribe());
        clickOnContinueBtn();
    }


}
