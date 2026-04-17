import dataFactories.RegisterDataFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjectsModels.HomePageObject;
import pageObjectsModels.LoginPageObject;
import pageObjectsModels.RegisterPageObject;

public class registerPageTestCases extends Base {

    @Test
    public void clickOnRegisterPageLink() {
        // Initialize helper method
        RegisterPageObject registerPage = navigateToRegisterPage();

        // Click on Login page link
        registerPage.clickOnLoginPageLink();

        // import page object and verify Returning Customer title
        var loginPage = new LoginPageObject(driver);
        String returningCustomerTitle = loginPage.getReturningCustomerTitle();
        Assert.assertEquals(returningCustomerTitle, "Returning Customer", "User not redirected to Login page");
    }

    @Test
    public void registerWithBlankData() {
        // Initialize helper method
        RegisterPageObject registerPage = navigateToRegisterPage();

        // Register with blank data
        registerPage.clickOnContinueBtn();

        // Verify Error message is displayed
        Assert.assertTrue(registerPage.isWarningMsgDisplayed(), "Warning message not displayed");
        Assert.assertTrue(registerPage.isFirstNameErrorMsgDisplayed(), "First name error message not displayed");
        Assert.assertTrue(registerPage.isLastNameErrorMsgDisplayed(), "Last name error message not displayed");
        Assert.assertTrue(registerPage.isEmailErrorMsgDisplayed(), "Email error message not displayed");
        Assert.assertTrue(registerPage.isTelephoneErrorMsgDisplayed(), "Telephone error message not displayed");
        Assert.assertTrue(registerPage.isPasswordErrorMsgDisplayed(), "Password error message not displayed");

        // Verify error message
        String warningMsg = registerPage.getWarningMsg();
        Assert.assertEquals(warningMsg, "Warning: You must agree to the Privacy Policy!", "Warning message not displayed or mismatched");

        String firstNameErrorMsg = registerPage.getFirstNameErrorText();
        Assert.assertEquals(firstNameErrorMsg, "First Name must be between 1 and 32 characters!", "First name error message not displayed or mismatched");

        String lastNameErrorMsg = registerPage.getLastNameErrorText();
        Assert.assertEquals(lastNameErrorMsg, "Last Name must be between 1 and 32 characters!", "Last name error message not displayed or mismatched");

        String emailErrorMsg = registerPage.getEmailErrorText();
        Assert.assertEquals(emailErrorMsg, "E-Mail Address does not appear to be valid!", "Email error message not displayed or mismatched");

        String telephoneErrorMsg = registerPage.getTelephoneErrorText();
        Assert.assertEquals(telephoneErrorMsg, "Telephone must be between 3 and 32 characters!", "Telephone error message not displayed or mismatched");

        String passwordErrorMsg = registerPage.getPasswordErrorText();
        Assert.assertEquals(passwordErrorMsg, "Password must be between 4 and 20 characters!", "Password error message not displayed or mismatched");
    }

    @Test
    public void passwordAndConfirmPasswordNotMatched() {
        // Initialize helper method
        RegisterPageObject registerPage = navigateToRegisterPage();

        // \Register with mismatched password & Confirm Password
        var user = RegisterDataFactory.passwordMismatched();

        registerPage.fillRegisterForm(user);
        registerPage.agreePrivacyPolicy();
        registerPage.clickOnContinueBtn();

        // Verify error message is displayed
        Assert.assertTrue(registerPage.isConfirmPasswordErrorMsgDisplayed(), "Confirm Password error message not displayed");

        // Verify error message
        String confirmMsg = registerPage.getConfirmPasswordErrorText();
        Assert.assertEquals(confirmMsg, "Password confirmation does not match password!", "Confirm Password error message not displayed or mismatched");
    }

    @Test
    public void verifyToEnterAlphabeticalValuesInPhoneNumber() {
        // Initialize helper method
        RegisterPageObject registerPage = navigateToRegisterPage();

        // Agree privacy policy
        registerPage.agreePrivacyPolicy();

        // Enter alphabetical values in phone number
        var user = RegisterDataFactory.alphabeticalValuesInPhoneNumber();
        registerPage.fillRegisterForm(user);
        registerPage.clickOnContinueBtn();

        // Verify error message displayed
        Assert.assertTrue(registerPage.isTelephoneErrorMsgDisplayed(), "Telephone error message not displayed");

        String telephoneErrorMsg = registerPage.getTelephoneErrorText();
        Assert.assertEquals(telephoneErrorMsg, "Telephone does not appear to be valid!", "Telephone error message not displayed or mismatched");
    }

    @Test
    public void fillRegisterFormWithValidData() {
        // Initialize helper method
        RegisterPageObject registerPage = navigateToRegisterPage();

        // Fill register form with valid data
        var user = RegisterDataFactory.validData();

        registerPage.fillRegisterForm(user);
        registerPage.agreePrivacyPolicy();
        registerPage.clickOnContinueBtn();

        // verify success registered message
        Assert.assertEquals(registerPage.getSuccessRegisteredMsg(), "Your Account Has Been Created!", "User registration failed");
    }
    
    // ============ Helper Methods ============

    private RegisterPageObject navigateToRegisterPage() {

        // Initialize Page Objects
        HomePageObject homePage = new HomePageObject(driver);
        RegisterPageObject registerPage = new RegisterPageObject(driver);

        // click on Register Button
        homePage.navigateToRegister();

        // Verify Register account title
        String actualPageTitle = registerPage.getRegisterAccountTitle();
        Assert.assertEquals(actualPageTitle, "Register Account", "Register Account Title mismatched");

        return registerPage;
    }
}
