import dataFactories.LoginDataFactory;
import dataFactories.RegisterDataFactory;
import dataObjects.LoginDetails;
import dataObjects.RegisterDetails;
import io.github.cdimascio.dotenv.Dotenv;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pageObjectsModels.ForgotPasswordPageObject;
import pageObjectsModels.HomePageObject;
import pageObjectsModels.LoginPageObject;
import pageObjectsModels.RegisterPageObject;
import utils.DriverFactory;
import utils.UserRegistrationHelper;

public class LoginPageTestCases extends Base {

    private final RegisterDetails user = RegisterDataFactory.validData();
    private LoginDetails validUser;

    @BeforeClass
    public void registerUserBeforeTest() {
        // Register a new user before each test without saving data
        Dotenv dotenv = Dotenv.load();
        driver = DriverFactory.getDriver(dotenv.get("BROWSER"));

        driver.get("https://naveenautomationlabs.com/opencart/index.php?route=account/register");

        var user1 = UserRegistrationHelper.registerUserWithoutSavingData(driver, user);
        validUser = LoginDataFactory.validLoginDetails(user1);
    }

    @Test
    public void loginWithBlankUser(){
        // initialize page object
        var loginPage = navigateToLoginPage();

        loginPage.clickOnLoginBtn();

        loginPage.isWarningMessageDisplayed();

        Assert.assertEquals(loginPage.getWarningMsgText(), "Warning: No match for E-Mail Address and/or Password.", "Warning message not matched");
    }

    @Test
    public void loginWithNotRegisterUser(){
        // initialize page object
        var loginPage = navigateToLoginPage();

        var notRegisterUser = LoginDataFactory.notRegisteredUser();

        loginPage.fillLoginForm(notRegisterUser);
        loginPage.clickOnLoginBtn();

        loginPage.isWarningMessageDisplayed();

        Assert.assertEquals(loginPage.getWarningMsgText(), "Warning: No match for E-Mail Address and/or Password.", "Warning message not matched");
    }

    @Test
    public void loginWithInvalidPassword(){
        // initialize page object
        var loginPage = navigateToLoginPage();

        var invalidPassword = LoginDataFactory.invalidPassword(validUser);

        loginPage.fillLoginForm(invalidPassword);
        loginPage.clickOnLoginBtn();

        loginPage.isWarningMessageDisplayed();

        Assert.assertEquals(loginPage.getWarningMsgText(), "Warning: No match for E-Mail Address and/or Password.", "Warning message not matched");
    }

    @Test
    public void loginWithValidUser() {
        // initialize page objects
        var loginPage = navigateToLoginPage();

        loginPage.fillLoginForm(validUser);
    }

    @Test
    public void verifyToClickOnContinueBtn(){
        // initialize page objects
        var loginPage =  navigateToLoginPage();
        var registerPage = new RegisterPageObject(driver);

        // click on continue button
        loginPage.clickOnContinueBtn();

        Assert.assertEquals(registerPage.getRegisterAccountTitle(), "Register Account", "Register Account Title mismatched or User not redirected to Register page");
    }

    @Test
    public void verifyToClickOnForgottenPasswordLink(){
        // initialize page objects
        var loginPage =  navigateToLoginPage();
        var forgotPasswordPage = new ForgotPasswordPageObject(driver);

        // click on forgotten password link
        loginPage.clickOnForgotPasswordLink();

        Assert.assertEquals(forgotPasswordPage.getForgotPasswordTitle(), "Forgot Your Password?", "Forgot Your Password? Title mismatched or User not redirected to Forgotten Password page");
    }

    // ============ Helper Methods ============
    private LoginPageObject navigateToLoginPage() {
        // initialize page objects
        var homePage = new HomePageObject(driver);
        var loginPage = new LoginPageObject(driver);

        // Click on Login link
        homePage.navigateToLogin();

        // Verify Returning Customer title
        String returningCustomerTitle = loginPage.getReturningCustomerTitle();
        Assert.assertEquals(returningCustomerTitle, "Returning Customer", "Returning Customer Title mismatched");

        return loginPage;
    }
}
