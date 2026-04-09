import dataFactories.LoginDataFactory;
import dataFactories.RegisterDataFactory;
import dataObjects.LoginDetails;
import dataObjects.RegisterDetails;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pageObjectsModels.HomePageObject;
import pageObjectsModels.LoginPageObject;
import pageObjectsModels.RegisterPageObject;
import utils.DriverFactory;

public class LoginPageTestCases extends Base {

    RegisterDetails user = RegisterDataFactory.validData();
    LoginDetails validUser = LoginDataFactory.validLoginDetails(user);

    @BeforeClass
    public void beforeClass() {
        var driver = DriverFactory.getDriver("chrome");
        driver.get("https://naveenautomationlabs.com/opencart/index.php?route=account/register");

        // call setup method to register user
        registerUser(driver);
    }

    @Test
    public void loginWithValidUser() {
        // initialize page objects
        LoginPageObject loginPage = navigateToLoginPage();

        loginPage.fillLoginForm(validUser);
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

    // ============ Setup Methods ============
    private void registerUser(WebDriver driver) {
        // initialized page object
        var registerPage = new RegisterPageObject(driver);

        // fill the register form
        registerPage.fillRegisterForm(user);
        registerPage.agreePrivacyPolicy();
        registerPage.clickOnContinueBtn();

        driver.quit();
    }
}
