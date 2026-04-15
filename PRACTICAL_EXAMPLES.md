# Practical Examples - Using UserRegistrationHelper

This document provides real-world examples of how to use the framework's new features.

## Example 1: Independent Login Test

### Scenario
You want to test user login without depending on the register test.

### Code

```java
import dataFactories.LoginDataFactory;
import dataObjects.LoginDetails;
import dataObjects.RegisterDetails;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageObjectsModels.HomePageObject;
import pageObjectsModels.LoginPageObject;
import utils.UserRegistrationHelper;

public class LoginPageTestCases extends Base {

    private RegisterDetails user;
    private LoginDetails validUser;

    @BeforeMethod
    public void registerUserBeforeTest() {
        // Step 1: Register a new user before each test without saving data
        // Each test gets a fresh, unique user
        user = UserRegistrationHelper.registerUserWithoutSavingData(driver);
        
        // Step 2: Convert RegisterDetails to LoginDetails
        // LoginDetails contains only email and password
        validUser = LoginDataFactory.validLoginDetails(user);
    }

    @Test
    public void loginWithValidUser() {
        // Initialize page object
        var loginPage = navigateToLoginPage();

        // Fill login form with the fresh user we created
        loginPage.fillLoginForm(validUser);
        
        // Click login button
        loginPage.clickOnLoginBtn();
        
        // Verify successful login
        Assert.assertTrue(loginPage.isDashboardDisplayed(), 
            "Dashboard not displayed after login");
    }

    @Test
    public void loginWithInvalidPassword() {
        // Initialize page object
        var loginPage = navigateToLoginPage();

        // Create invalid password using the same user email
        var invalidPassword = LoginDataFactory.invalidPassword(validUser);

        // Try to login with invalid password
        loginPage.fillLoginForm(invalidPassword);
        loginPage.clickOnLoginBtn();

        // Verify error message
        loginPage.isWarningMessageDisplayed();
        Assert.assertEquals(loginPage.getWarningMsgText(), 
            "Warning: No match for E-Mail Address and/or Password.", 
            "Warning message not matched");
    }

    // ============ Helper Methods ============
    private LoginPageObject navigateToLoginPage() {
        // Initialize page objects
        var homePage = new HomePageObject(driver);
        var loginPage = new LoginPageObject(driver);

        // Click on Login link
        homePage.navigateToLogin();

        // Verify Returning Customer title
        String returningCustomerTitle = loginPage.getReturningCustomerTitle();
        Assert.assertEquals(returningCustomerTitle, "Returning Customer", 
            "Returning Customer Title mismatched");

        return loginPage;
    }
}
```

### How It Works
1. **@BeforeMethod** runs before EACH test
2. A **fresh user is registered** every time
3. Each test is **completely independent**
4. Tests can run in **any order** or in **parallel**

---

## Example 2: Forgot Password Test Suite

### Scenario
Testing forgot password functionality with multiple scenarios.

### Code

```java
import dataFactories.LoginDataFactory;
import dataObjects.LoginDetails;
import dataObjects.RegisterDetails;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageObjectsModels.ForgotPasswordPageObject;
import pageObjectsModels.HomePageObject;
import utils.UserRegistrationHelper;

public class ForgotPasswordTestCases extends Base {

    private RegisterDetails registeredUser;
    private ForgotPasswordPageObject forgotPasswordPage;

    @BeforeMethod
    public void setupUserForForgotPassword() {
        // Create a fresh registered user for each test
        registeredUser = UserRegistrationHelper.registerUserWithoutSavingData(driver);
        
        // Initialize forgot password page object
        forgotPasswordPage = new ForgotPasswordPageObject(driver);
    }

    @Test
    public void forgotPasswordWithRegisteredEmail() {
        // Navigate to forgot password page
        HomePageObject homePage = new HomePageObject(driver);
        homePage.navigateToForgotPassword();

        // Enter the email of our registered user
        forgotPasswordPage.enterEmail(registeredUser.getEmail());
        
        // Click reset button
        forgotPasswordPage.clickResetButton();

        // Verify success message
        Assert.assertTrue(forgotPasswordPage.isSuccessMessageDisplayed(),
            "Success message not displayed");
    }

    @Test
    public void forgotPasswordWithUnregisteredEmail() {
        // Navigate to forgot password page
        HomePageObject homePage = new HomePageObject(driver);
        homePage.navigateToForgotPassword();

        // Try with an unregistered email
        forgotPasswordPage.enterEmail("unregistered@example.com");
        forgotPasswordPage.clickResetButton();

        // Verify error message
        Assert.assertTrue(forgotPasswordPage.isErrorMessageDisplayed(),
            "Error message not displayed");
    }

    @Test
    public void forgotPasswordWithEmptyEmail() {
        // Navigate to forgot password page
        HomePageObject homePage = new HomePageObject(driver);
        homePage.navigateToForgotPassword();

        // Try to submit without entering email
        forgotPasswordPage.clickResetButton();

        // Verify validation error
        Assert.assertTrue(forgotPasswordPage.isValidationErrorDisplayed(),
            "Validation error not displayed");
    }
}
```

---

## Example 3: Multi-Step User Journey Test

### Scenario
Testing a complete user journey from registration to checkout.

### Code

```java
import dataObjects.RegisterDetails;
import dataObjects.ProductDetails;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageObjectsModels.*;
import utils.UserRegistrationHelper;
import dataFactories.ProductDataFactory;

public class UserJourneyTestCases extends Base {

    private RegisterDetails user;
    private ProductDetails product;

    @BeforeMethod
    public void setupUserAndProduct() {
        // Step 1: Register a new user
        user = UserRegistrationHelper.registerUserWithoutSavingData(driver);
        
        // Step 2: Get test product data
        product = ProductDataFactory.getRandomProduct();
    }

    @Test
    public void completeCheckoutJourney() {
        // Step 1: Search for product
        HomePageObject homePage = new HomePageObject(driver);
        SearchPageObject searchPage = homePage.searchForProduct(product.getName());

        // Step 2: Add product to cart
        searchPage.clickOnProduct(product.getName());
        ProductDetailPageObject productPage = new ProductDetailPageObject(driver);
        productPage.addToCart();

        // Step 3: Navigate to cart
        homePage.navigateToCart();
        ShoppingCartPageObject cartPage = new ShoppingCartPageObject(driver);
        
        // Step 4: Verify product in cart
        Assert.assertTrue(cartPage.isProductInCart(product.getName()),
            "Product not found in cart");
        
        // Step 5: Proceed to checkout
        LoginDetails userLogin = LoginDataFactory.validLoginDetails(user);
        cartPage.proceedToCheckout();
        
        // Step 6: Fill checkout details
        CheckoutPageObject checkoutPage = new CheckoutPageObject(driver);
        checkoutPage.enterBillingDetails(user);
        checkoutPage.selectShippingMethod();
        
        // Step 7: Complete order
        checkoutPage.completeOrder();
        
        // Step 8: Verify order confirmation
        Assert.assertTrue(checkoutPage.isOrderConfirmed(),
            "Order confirmation not displayed");
    }
}
```

---

## Example 4: Custom User Data Test

### Scenario
Testing with specific user attributes (e.g., business account).

### Code

```java
import dataObjects.RegisterDetails;
import dataObjects.LoginDetails;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageObjectsModels.LoginPageObject;
import utils.UserRegistrationHelper;

public class BusinessAccountTestCases extends Base {

    private RegisterDetails businessUser;

    @BeforeMethod
    public void setupBusinessAccount() {
        // Create a business account user with specific details
        businessUser = new RegisterDetails();
        businessUser.setFirstName("Acme");
        businessUser.setLastName("Corporation");
        businessUser.setEmail("business@acmecorp.com");
        businessUser.setTelephone("+1-800-ACME-123");
        businessUser.setPassword("BusinessPassword123!");
        businessUser.setConfirmPassword("BusinessPassword123!");
        businessUser.setSubscribe("Yes");

        // Register the business user
        UserRegistrationHelper.registerUserWithoutSavingData(driver, businessUser);
    }

    @Test
    public void businessAccountCanLogin() {
        // Create login details from business user
        LoginDetails loginDetails = new LoginDetails();
        loginDetails.setEmail(businessUser.getEmail());
        loginDetails.setPassword(businessUser.getPassword());

        // Navigate to login
        HomePageObject homePage = new HomePageObject(driver);
        homePage.navigateToLogin();

        // Login with business account
        LoginPageObject loginPage = new LoginPageObject(driver);
        loginPage.fillLoginForm(loginDetails);
        loginPage.clickOnLoginBtn();

        // Verify login success
        Assert.assertTrue(loginPage.isUserLoggedIn(),
            "Business user could not login");
    }

    @Test
    public void businessAccountPhoneNumber() {
        // This test verifies the phone number was saved correctly
        AccountPageObject accountPage = new AccountPageObject(driver);
        
        String savedPhone = accountPage.getTelephoneNumber();
        Assert.assertEquals(savedPhone, businessUser.getTelephone(),
            "Telephone number not saved correctly");
    }
}
```

---

## Example 5: Comparison - Before and After

### BEFORE (File-based approach)

```java
// OLD APPROACH - DO NOT USE THIS
public class LoginPageTestCases extends Base {

    @BeforeClass
    public void beforeClass() {
        // Create separate driver
        Dotenv dotenv = Dotenv.load();
        WebDriver tempDriver = DriverFactory.getDriver(dotenv.get("BROWSER"));
        tempDriver.get("https://...");

        // Register user in SAME method
        RegisterPageObject registerPage = new RegisterPageObject(tempDriver);
        RegisterDetails user = RegisterDataFactory.validData();
        registerPage.fillRegisterForm(user);
        registerPage.agreePrivacyPolicy();
        registerPage.clickOnContinueBtn();

        // Save to file
        UserDataManager.saveUser(user);
        tempDriver.quit();
    }

    @Test
    public void loginTest() {
        // Read from file
        RegisterDetails user = UserDataManager.getUser();
        LoginDetails loginDetails = LoginDataFactory.validLoginDetails(user);
        
        // Test code...
    }
}
```

**Problems with old approach:**
- ❌ Separate driver initialization
- ❌ Saves data to file (dependency)
- ❌ Test depends on file existing
- ❌ Cannot run tests in parallel
- ❌ Cleanup issues
- ❌ Hard to maintain

### AFTER (Helper-based approach)

```java
// NEW APPROACH - USE THIS
public class LoginPageTestCases extends Base {

    private RegisterDetails user;
    private LoginDetails validUser;

    @BeforeMethod
    public void registerUserBeforeTest() {
        // One line registration!
        user = UserRegistrationHelper.registerUserWithoutSavingData(driver);
        validUser = LoginDataFactory.validLoginDetails(user);
    }

    @Test
    public void loginTest() {
        // Use user directly
        LoginPageObject loginPage = navigateToLoginPage();
        loginPage.fillLoginForm(validUser);
        loginPage.clickOnLoginBtn();
        // Assertions...
    }
}
```

**Benefits of new approach:**
- ✅ Uses existing driver from Base class
- ✅ No file dependencies
- ✅ Tests are independent
- ✅ Can run tests in parallel
- ✅ Automatic cleanup via @AfterMethod
- ✅ Easy to maintain and extend

---

## Example 6: Data-Driven Tests with UserRegistrationHelper

### Scenario
Testing login with multiple user types.

### Code

```java
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.UserRegistrationHelper;

public class DataDrivenLoginTestCases extends Base {

    private RegisterDetails user;

    @BeforeMethod
    public void setupUser() {
        // Register fresh user for each iteration
        user = UserRegistrationHelper.registerUserWithoutSavingData(driver);
    }

    @DataProvider(name = "loginScenarios")
    public Object[][] loginScenarios() {
        return new Object[][] {
            { "validPassword", true },
            { "invalidPassword", false },
            { "emptyPassword", false },
            { "nullPassword", false }
        };
    }

    @Test(dataProvider = "loginScenarios")
    public void testLoginWithVariousPasswords(String scenario, boolean shouldSucceed) {
        LoginPageObject loginPage = navigateToLoginPage();

        // Determine password based on scenario
        String password = determinePassword(scenario);

        // Attempt login
        loginPage.fillEmail(user.getEmail());
        loginPage.fillPassword(password);
        loginPage.clickOnLoginBtn();

        // Verify result based on scenario
        if (shouldSucceed) {
            Assert.assertTrue(loginPage.isDashboardDisplayed());
        } else {
            Assert.assertTrue(loginPage.isErrorMessageDisplayed());
        }
    }

    private String determinePassword(String scenario) {
        switch (scenario) {
            case "validPassword":
                return user.getPassword();
            case "invalidPassword":
                return "WrongPassword123";
            case "emptyPassword":
                return "";
            case "nullPassword":
                return null;
            default:
                return "";
        }
    }

    private LoginPageObject navigateToLoginPage() {
        HomePageObject homePage = new HomePageObject(driver);
        LoginPageObject loginPage = new LoginPageObject(driver);
        homePage.navigateToLogin();
        return loginPage;
    }
}
```

---

## Example 7: Parameterized Tests

### Scenario
Testing with different browser configurations.

### .env File
```env
BROWSER=chrome
BASE_URL=https://naveenautomationlabs.com/opencart/
```

### TestNG Suite Configuration (testng.xml)
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE suite SYSTEM "http://testng.org/testng-1.0.dtd">
<suite name="Login Test Suite" parallel="tests" thread-count="3">
    
    <test name="Chrome Tests">
        <parameter name="browser" value="chrome"/>
        <classes>
            <class name="LoginPageTestCases"/>
            <class name="ForgotPasswordTestCases"/>
        </classes>
    </test>

    <test name="Firefox Tests">
        <parameter name="browser" value="firefox"/>
        <classes>
            <class name="LoginPageTestCases"/>
            <class name="ForgotPasswordTestCases"/>
        </classes>
    </test>

    <test name="Edge Tests">
        <parameter name="browser" value="edge"/>
        <classes>
            <class name="LoginPageTestCases"/>
            <class name="ForgotPasswordTestCases"/>
        </classes>
    </test>
</suite>
```

**Benefits:**
- Run same tests in different browsers
- Parallel execution (3 browsers simultaneously)
- Same helper works for all browsers
- Tests automatically register users in each browser

---

## Running These Examples

### Run all tests
```bash
mvn test
```

### Run specific test class
```bash
mvn test -Dtest=LoginPageTestCases
```

### Run specific test method
```bash
mvn test -Dtest=LoginPageTestCases#loginWithValidUser
```

### Run with specific browser
Update `.env`:
```env
BROWSER=firefox
```

Then:
```bash
mvn test
```

### Run tests in parallel
```bash
mvn test -Dsuites=testng.xml
```

---

## Key Takeaways

1. **Always use @BeforeMethod** for setup with UserRegistrationHelper
2. **Each test gets a fresh user** - no dependencies
3. **Tests can run in any order** - truly independent
4. **No file management needed** - cleaner code
5. **Reusable across test classes** - DRY principle
6. **Easy to extend** - just add more test methods


