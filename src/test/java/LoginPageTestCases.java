import dataFactories.LoginDataFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjectsModels.HomePageObject;
import pageObjectsModels.LoginPageObject;

public class LoginPageTestCases extends Base{
    @Test
    public void loginWithValidUser() {
        // initialize page objects
        LoginPageObject loginPage = navigateToLoginPage();

        // Login with valid user
        var user = LoginDataFactory.validLoginDetails();
        loginPage.fillLoginForm(user);
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
