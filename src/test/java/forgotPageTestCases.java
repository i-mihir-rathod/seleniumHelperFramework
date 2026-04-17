import dataFactories.ForgotPasswordDataFactory;
import dataFactories.RegisterDataFactory;
import dataObjects.ForgotPasswordDetails;
import dataObjects.RegisterDetails;
import io.github.cdimascio.dotenv.Dotenv;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pageObjectsModels.ForgotPasswordPageObject;
import pageObjectsModels.LoginPageObject;
import utils.DriverFactory;
import utils.UserRegistrationHelper;

public class forgotPageTestCases extends Base{

    private final RegisterDetails registerUser = RegisterDataFactory.validData();
    private ForgotPasswordDetails validUser;

    @BeforeClass
    private void registerUserBeforeTest(){
        // Register a new user before each test without saving data
        Dotenv dotenv = Dotenv.load();
        driver = DriverFactory.getDriver(dotenv.get("BROWSER"));

        driver.get("https://naveenautomationlabs.com/opencart/index.php?route=account/register");

        var user1 = UserRegistrationHelper.registerUserWithoutSavingData(driver, registerUser);
        validUser = ForgotPasswordDataFactory.validEmail(user1);
    }

    @Test
    public void verifyToClickOnBackBtn(){
        // Initialize page objects navigate to forgot password page
        var loginPage = new LoginPageObject(driver);
        var forgotPasswordPage = navigateToForgotPasswordPage();

        // click on back button
        forgotPasswordPage.clickOnBackBtn();

        // verify user is navigated to login page or not?
        Assert.assertEquals(loginPage.getReturningCustomerTitle(), "Returning Customer", "User is not navigated to login page");
    }

    @Test
    public void verifyBlankEmail(){
        // navigate to forgot password page
        var forgotPasswordPage = navigateToForgotPasswordPage();

        // keep email blank and click on Continue button
        forgotPasswordPage.clickOnContinueBtn();

        // check warning message is displayed? and message is correct?
        Assert.assertTrue(forgotPasswordPage.isWarningMsgDisplayed(), "Warning message is not displayed");
        Assert.assertEquals(forgotPasswordPage.getWarningMsg(), "Warning: The E-Mail Address was not found in our records, please try again!", "Warning message mismatch");
    }

    @Test
    public void verifyNotRegisteredEmail(){
        // navigate to forgot password page
        var forgotPasswordPage = navigateToForgotPasswordPage();

        // get data factory
        var user = ForgotPasswordDataFactory.notRegisteredUser();

        // enter not registered email and click on Continue button
        forgotPasswordPage.enterEmail(user);
        forgotPasswordPage.clickOnContinueBtn();

        // check warning message is displayed? and message is correct?
        Assert.assertTrue(forgotPasswordPage.isWarningMsgDisplayed(), "Warning message is not displayed");
        Assert.assertEquals(forgotPasswordPage.getWarningMsg(), "Warning: The E-Mail Address was not found in our records, please try again!", "Warning message mismatch");
    }

    @Test
    public void verifyInvalidEmail(){
        // navigate to forgot password page
        var forgotPasswordPage = navigateToForgotPasswordPage();

        // get data factory
        var user = ForgotPasswordDataFactory.invalidUser(validUser);

        // enter not registered email and click on Continue button
        forgotPasswordPage.enterEmail(user);
        forgotPasswordPage.clickOnContinueBtn();

        // check warning message is displayed? and message is correct?
        Assert.assertTrue(forgotPasswordPage.isWarningMsgDisplayed(), "Warning message is not displayed");
        Assert.assertEquals(forgotPasswordPage.getWarningMsg(), "Warning: The E-Mail Address was not found in our records, please try again!", "Warning message mismatch");
    }

    @Test
    public void verifyValidEmail(){
        // Initialize page objects and navigate to forgot password page
        var loginPage = new LoginPageObject(driver);
        var forgotPasswordPage = navigateToForgotPasswordPage();

        // enter not registered email and click on Continue button
        forgotPasswordPage.enterEmail(validUser);
        forgotPasswordPage.clickOnContinueBtn();

        // check warning message is displayed? and message is correct?
        Assert.assertTrue(loginPage.isWarningMessageDisplayed(), "Warning message is not displayed");
        Assert.assertEquals(loginPage.getWarningMsgText(), "An email with a confirmation link has been sent your email address.", "Warning message mismatch");
    }

    // ============ Helper Methods ============
    private ForgotPasswordPageObject navigateToForgotPasswordPage() {
        // redirect to forgot password page
        driver.get("https://naveenautomationlabs.com/opencart/index.php?route=account/forgotten");

        // Initiate page objects
        var forgotPasswordPage = new ForgotPasswordPageObject(driver);

        // verify forgot password title
        Assert.assertEquals(forgotPasswordPage.getForgotPasswordTitle(), "Forgot Your Password?", "Forgot password page title mismatch");

        return forgotPasswordPage;
    }
}
