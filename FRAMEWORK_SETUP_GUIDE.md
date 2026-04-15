# Framework Setup and Usage Guide

## Summary of Changes

This guide explains the new architecture and how to use the framework for independent, self-contained tests without file dependencies.

## Problem Solved

### Before
- Login tests depended on the register test case running first
- Login tests required a `user.txt` file created by register tests
- Deleting the `.txt` file would break login tests
- Tests could not run independently

### After
- Login tests are completely independent
- No file dependencies or external data sources needed
- Tests can run in any order
- Register helper method creates users on-the-fly during test setup

## Architecture Overview

### 1. Browser Initialization Flow

```
Base.java (@BeforeSuite)
    ↓
Load environment from .env file
    ↓
Get BROWSER name (chrome/firefox/edge)
    ↓
Base.java (@BeforeMethod)
    ↓
Pass browser name to DriverFactory.getDriver(browserName)
    ↓
DriverFactory checks browser type
    ↓
Initialize appropriate WebDriver (Chrome/Firefox/Edge)
    ↓
Configure driver options (disable password manager, etc.)
    ↓
Maximize window and return driver
```

### 2. Test Setup Flow (Example: Login Tests)

```
LoginPageTestCases.java (@BeforeMethod)
    ↓
Call UserRegistrationHelper.registerUserWithoutSavingData(driver)
    ↓
Generate random valid user data
    ↓
Navigate to register page
    ↓
Fill registration form
    ↓
Agree to privacy policy
    ↓
Click continue button
    ↓
Return RegisterDetails object (with email & password)
    ↓
Convert to LoginDetails using LoginDataFactory
    ↓
Test method executes with valid user
```

## New Utility: UserRegistrationHelper

### Location
`src/main/java/utils/UserRegistrationHelper.java`

### Purpose
Handles user registration for test setup without saving data to files, ensuring test independence.

### Methods

#### 1. `registerUserWithoutSavingData(WebDriver driver)`
**What it does:**
- Generates random valid user data
- Registers the user on the application
- Returns the user details for login tests

**Usage:**
```java
@BeforeMethod
public void setup() {
    RegisterDetails user = UserRegistrationHelper.registerUserWithoutSavingData(driver);
    LoginDetails loginData = LoginDataFactory.validLoginDetails(user);
}
```

#### 2. `registerUserWithoutSavingData(WebDriver driver, RegisterDetails user)`
**What it does:**
- Registers a user with specific provided data
- Useful for custom test scenarios

**Usage:**
```java
@BeforeMethod
public void setup() {
    RegisterDetails customUser = new RegisterDetails();
    customUser.setFirstName("John");
    customUser.setEmail("john@example.com");
    // ... set other fields
    
    UserRegistrationHelper.registerUserWithoutSavingData(driver, customUser);
}
```

#### 3. `navigateToRegisterPage(WebDriver driver)`
**What it does:**
- Navigates to register page without performing registration
- Useful for testing the registration page itself

**Usage:**
```java
@Test
public void testRegisterPageElements() {
    RegisterPageObject registerPage = 
        UserRegistrationHelper.navigateToRegisterPage(driver);
    String title = registerPage.getRegisterAccountTitle();
}
```

## Configuration Files

### .env File (Root Directory)
```env
BROWSER=chrome
BASE_URL=https://naveenautomationlabs.com/opencart/
```

**Available Options:**
- **BROWSER**: `chrome`, `firefox`, or `edge`
- **BASE_URL**: URL of the application under test

### Base.java Setup
The Base class handles:
1. Loading `.env` variables using dotenv library
2. Passing browser name to DriverFactory
3. Initializing WebDriver before each test
4. Maximizing browser window
5. Navigating to base URL
6. Capturing screenshots on failure
7. Cleaning up resources after test

## Usage Examples

### Example 1: Independent Login Test

```java
public class LoginPageTestCases extends Base {
    private RegisterDetails user;
    private LoginDetails validUser;

    @BeforeMethod
    public void registerUserBeforeTest() {
        // Register a fresh user before each test
        user = UserRegistrationHelper.registerUserWithoutSavingData(driver);
        
        // Convert to login details
        validUser = LoginDataFactory.validLoginDetails(user);
    }

    @Test
    public void loginWithValidUser() {
        // Test can now run independently
        LoginPageObject loginPage = navigateToLoginPage();
        loginPage.fillLoginForm(validUser);
        loginPage.clickOnLoginBtn();
        
        // Assertions...
    }
}
```

### Example 2: Forgot Password Test (Reusing Helper)

```java
public class ForgotPasswordTestCases extends Base {
    private RegisterDetails user;

    @BeforeMethod
    public void setupUserForForgotPassword() {
        // Register a user for forgot password workflow
        user = UserRegistrationHelper.registerUserWithoutSavingData(driver);
    }

    @Test
    public void testForgotPasswordFlow() {
        ForgotPasswordPage forgotPage = navigateToForgotPassword();
        forgotPage.enterEmail(user.getEmail());
        forgotPage.clickResetButton();
        
        // Assertions...
    }
}
```

### Example 3: Custom User Registration Test

```java
public class CustomUserTestCases extends Base {

    @BeforeMethod
    public void setupCustomUser() {
        // Create custom user with specific attributes
        RegisterDetails customUser = new RegisterDetails();
        customUser.setFirstName("Jane");
        customUser.setLastName("Smith");
        customUser.setEmail("jane.smith@example.com");
        customUser.setTelephone("9876543210");
        customUser.setPassword("Test@1234");
        customUser.setConfirmPassword("Test@1234");
        customUser.setSubscribe("No");

        UserRegistrationHelper.registerUserWithoutSavingData(driver, customUser);
    }

    @Test
    public void testCustomUserLogin() {
        // Test with custom user...
    }
}
```

## DriverFactory Details

### Location
`src/test/java/utils/DriverFactory.java`

### Supported Browsers

#### Chrome (Default)
- Disables credentials manager
- Disables password manager
- Disables password manager leak detection

#### Firefox
- Disables credentials manager
- Disables password manager
- Disables password manager leak detection
- Disables notifications

#### Edge
- Same configuration as Chrome

### How to Change Browser

**Method 1: Modify .env file**
```env
BROWSER=firefox
```

**Method 2: Programmatically (not recommended)**
Change the `Base.java` if you need to override the default:
```java
browserName = "firefox"; // Overrides .env setting
```

## Best Practices

### 1. Always Use @BeforeMethod for Setup
```java
@BeforeMethod
public void setup() {
    // Create user here, not in @Test
    user = UserRegistrationHelper.registerUserWithoutSavingData(driver);
}

@Test
public void myTest() {
    // Use user here
}
```

### 2. Keep Tests Focused
Each test should test one specific behavior:
```java
@Test
public void loginWithValidCredentials() {
    // Test valid login only
}

@Test
public void loginWithInvalidPassword() {
    // Test invalid password only
}
```

### 3. Use Data Factories for Data Generation
```java
// Good
RegisterDetails user = RegisterDataFactory.validData();

// Avoid
RegisterDetails user = new RegisterDetails();
user.setFirstName("John");  // Hard-coded data
```

### 4. Leverage Helper Methods
```java
// Create reusable helpers
private LoginPageObject navigateToLoginPage() {
    HomePageObject home = new HomePageObject(driver);
    LoginPageObject login = new LoginPageObject(driver);
    home.navigateToLogin();
    return login;
}

@Test
public void testLogin() {
    LoginPageObject loginPage = navigateToLoginPage();
    // Use page object...
}
```

## Troubleshooting

### Problem: Tests fail with "Element not found"
**Solution:** Ensure you're navigating to the page before trying to find elements
```java
@BeforeMethod
public void setup() {
    // This navigates to the app automatically
    user = UserRegistrationHelper.registerUserWithoutSavingData(driver);
}
```

### Problem: Driver initialization fails
**Solution:** 
1. Check if Chrome/Firefox/Edge is installed
2. Verify `.env` file exists in project root
3. Check browser name is correct in `.env`

### Problem: Test runs but elements are not visible
**Solution:** 
1. Check if WebDriver is waiting for elements
2. Use explicit waits (already implemented in SeleniumHelper)
3. Verify locators are correct

### Problem: Screenshots not saved on failure
**Solution:** 
1. Ensure `Screenshots/` directory exists
2. Check Base.java AfterMethod is being executed
3. Verify file path in `FilePathHelper.java`

## File Structure Summary

| File | Purpose |
|------|---------|
| `Base.java` | Base test class with WebDriver setup |
| `DriverFactory.java` | WebDriver initialization for different browsers |
| `UserRegistrationHelper.java` | User registration utility for test setup |
| `RegisterDataFactory.java` | Random test data generation |
| `LoginDataFactory.java` | Login data conversion utilities |
| `.env` | Environment configuration |

## Running Tests

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
Update `.env` file:
```env
BROWSER=firefox
mvn test
```

## Key Improvements

✅ **Test Independence**: Tests don't depend on other tests or external files  
✅ **Reusability**: UserRegistrationHelper can be used across multiple test classes  
✅ **Flexibility**: Supports random data generation or custom data  
✅ **Maintainability**: Clear separation of concerns (Utils, Page Objects, Tests)  
✅ **Scalability**: Easy to add new test classes following the same pattern  
✅ **Reliability**: Explicit waits and robust element handling  
✅ **Debugging**: Screenshots captured on test failure  


