# Selenium Automation Framework for OpenCart

This is a robust Selenium WebDriver-based automation framework designed for testing the OpenCart e-commerce platform. The framework follows the Page Object Model (POM) design pattern and incorporates best practices for maintainable and scalable test automation.

## Table of Contents
- [Overview](#overview)
- [Architecture](#architecture)
- [Prerequisites](#prerequisites)
- [Setup](#setup)
- [Configuration](#configuration)
- [Running Tests](#running-tests)
- [Project Structure](#project-structure)
- [Writing New Tests](#writing-new-tests)
- [Test Utilities](#test-utilities)
- [Reporting](#reporting)
- [Contributing](#contributing)

## Overview

The framework automates testing of the OpenCart demo site located at `https://naveenautomationlabs.com/opencart/`. It includes comprehensive test coverage for key e-commerce functionalities such as:

- User registration and login
- Product search and browsing
- Shopping cart operations
- Currency selection
- Navigation and UI interactions

### Test Independence
To ensure tests can run independently without external dependencies (e.g., files or prior test execution), the framework includes setup helpers. For example, login tests use a **UserRegistrationHelper** to create users on-the-fly without saving data to files, allowing login tests to execute standalone without depending on the register test case.

## Architecture

The framework is built using the following technologies and patterns:

- **Selenium WebDriver 4.41.0**: For browser automation
- **TestNG 7.12.0**: For test execution and reporting
- **Page Object Model (POM)**: For maintainable page representations
- **Data-Driven Testing**: Using factories and data objects
- **Utility Classes**: For common operations like waits, JavaScript execution, and element interactions

### Key Components

1. **Page Objects**: Encapsulate page elements and actions
2. **Base Classes**: Provide common setup and teardown
3. **Data Factories**: Generate test data
4. **Data Objects**: Represent test data structures
5. **Utils**: Helper classes for waits, JavaScript, Selenium operations, random data generation

## Prerequisites

- Java 24 (or compatible version)
- Maven 3.x
- Chrome browser (for ChromeDriver)
- Internet connection (to access the test site)

## Setup

1. **Clone or download the project** to your local machine.

2. **Navigate to the project directory**:
   ```
   cd seleniumHelperFramework
   ```

3. **Install dependencies** using Maven:
   ```
   mvn clean install
   ```

4. **Ensure ChromeDriver is compatible** with your Chrome browser version. Selenium 4 manages drivers automatically.

## Configuration

### Environment Variables Setup

The framework uses a `.env` file to configure browser and base URL settings. Create a `.env` file in the project root with the following variables:

```env
BROWSER=chrome
BASE_URL=https://naveenautomationlabs.com/opencart/
```

**Available Browsers:**
- `chrome` - Google Chrome (default)
- `firefox` - Mozilla Firefox
- `edge` - Microsoft Edge

### Browser Configuration in Base.java

The `Base.java` class is configured to:
1. Load environment variables from `.env` file using dotenv library
2. Initialize the WebDriver based on the `BROWSER` variable
3. Maximize the browser window
4. Navigate to the `BASE_URL` before each test
5. Capture screenshots on test failure
6. Clean up resources after each test

```java
@BeforeSuite
public void beforeSuite() {
    // Load environment variables from .env file
    Dotenv dotenv = Dotenv.load();
    baseUrl = dotenv.get("BASE_URL");
    browserName = dotenv.get("BROWSER");
}

@BeforeMethod
public void beforeMethod() {
    // Initialize WebDriver based on browser name
    driver = DriverFactory.getDriver(browserName);
    driver.get(baseUrl);
}
```

### DriverFactory Integration

The `DriverFactory` class handles WebDriver initialization based on the browser name string passed from `Base.java`:

```java
public static WebDriver getDriver(String browser) {
    WebDriver driver;
    if (browser.equalsIgnoreCase("chrome")) {
        driver = setChromeDriver();
    } else if (browser.equalsIgnoreCase("firefox")) {
        driver = setFireFoxDriver();
    } else if (browser.equalsIgnoreCase("edge")) {
        driver = setEdgeDriver();
    } else {
        driver = setChromeDriver(); // Default to Chrome
    }
    
    driver.manage().window().maximize();
    return driver;
}
```

**How it works:**
1. The `Base.java` reads `BROWSER` from `.env` file
2. This browser name is passed to `DriverFactory.getDriver(browserName)`
3. `DriverFactory` checks the browser name and initializes the appropriate WebDriver
4. The driver is configured with appropriate options (credentials manager disabled, etc.)
5. The driver is maximized and returned for use in tests

## Running Tests

### Run All Tests
```
mvn test
```

### Run Specific Test Class
```
mvn test -Dtest=homePageTestCases
mvn test -Dtest=LoginPageTestCases
mvn test -Dtest=RegisterPageTestCases
mvn test -Dtest=CartPageTestCases
```

### Run Specific Test Method
```
mvn test -Dtest=homePageTestCases#addToCartOneProduct
```

### Run Tests with Custom Configuration
You can modify the `Base.java` class to change browser settings or test URL.

## Project Structure

```
seleniumHelperFramework/
├── .env                              # Environment configuration (BROWSER, BASE_URL)
├── pom.xml                           # Maven configuration
├── README.md                         # Project documentation
├── src/
│   ├── main/java/
│   │   ├── dataFactories/            # Test data generation factories
│   │   │   ├── LoginDataFactory.java
│   │   │   ├── ProductDataFactory.java
│   │   │   └── RegisterDataFactory.java
│   │   ├── dataObjects/              # Data transfer objects (DTOs)
│   │   │   ├── LoginDetails.java
│   │   │   ├── ProductDetails.java
│   │   │   └── RegisterDetails.java
│   │   ├── pageObjectsModels/        # Page Object classes
│   │   │   ├── BasePageObject.java
│   │   │   ├── HomePageObject.java
│   │   │   ├── LoginPageObject.java
│   │   │   ├── RegisterPageObject.java
│   │   │   ├── SearchPageObject.java
│   │   │   └── ShoppingCartPageObject.java
│   │   └── utils/                    # Utility classes
│   │       ├── DriverFactory.java    # WebDriver factory (now in test/utils)
│   │       ├── FilePathHelper.java
│   │       ├── FluentWaitUtils.java
│   │       ├── JavaScriptHelper.java
│   │       ├── RandomDataGenerator.java
│   │       ├── SeleniumHelper.java
│   │       ├── UserDataManager.java
│   │       ├── UserRegistrationHelper.java (NEW - Helper for registration setup)
│   │       └── WaitUtils.java
│   └── test/java/
│       ├── Base.java                 # Base test class with WebDriver management
│       ├── CartPageTestCases.java    # Cart page tests
│       ├── homePageTestCases.java    # Home page tests
│       ├── LoginPageTestCases.java   # Login page tests (now with independent setup)
│       ├── RegisterPageTestCases.java # Registration tests
│       └── utils/
│           └── DriverFactory.java    # WebDriver factory
├── target/                           # Compiled classes and test reports
├── Screenshots/                      # Failure screenshots (auto-generated)
├── uploadFiles/                      # Test files for upload tests
└── testng.xml                        # TestNG configuration
```

## Writing New Tests

### 1. Create Page Object (if needed)
Extend `BasePageObject` and define locators and actions:

```java
public class NewPageObject extends BasePageObject {
    private final By newElement = By.id("new-element");
    
    public NewPageObject(WebDriver driver) {
        super(driver);
    }
    
    public void performAction() {
        seleniumHelper.clickOnElement(newElement);
    }
}
```

### 2. Create Data Factory (if needed)
```java
public class NewDataFactory {
    public static NewDataObject createData() {
        return new NewDataObject("value1", "value2");
    }
}
```

### 3. Create Data Object (if needed)
```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewDataObject {
    private String field1;
    private String field2;
}
```

### 4. Write Test Case
Extend `Base` and use TestNG annotations:

```java
public class NewTestCases extends Base {
    @Test
    public void newTest() {
        // Step 1: Initialize page object
        NewPageObject page = new NewPageObject(driver);
        
        // Step 2: Perform actions
        page.performAction();
        
        // Step 3: Verify results
        Assert.assertTrue(condition, "Test failed");
    }
}
```

## Test Utilities

### UserRegistrationHelper

The `UserRegistrationHelper` is a utility class that handles user registration for test setup without saving data to files. This ensures test independence and allows tests to run without depending on other tests.

**Key Features:**
- Registers users on-the-fly during test setup
- Generates random valid user data automatically
- Does not save data to files (no dependencies)
- Reusable across multiple test scenarios

**Usage Examples:**

#### 1. Basic Registration (generates random user data)
```java
public class LoginPageTestCases extends Base {
    private RegisterDetails user;
    private LoginDetails validUser;

    @BeforeMethod
    public void registerUserBeforeTest() {
        // Register a new user before each test without saving data
        user = UserRegistrationHelper.registerUserWithoutSavingData(driver);
        validUser = LoginDataFactory.validLoginDetails(user);
    }

    @Test
    public void loginWithValidUser() {
        LoginPageObject loginPage = navigateToLoginPage();
        loginPage.fillLoginForm(validUser);
        loginPage.clickOnLoginBtn();
        // Assertions...
    }
}
```

#### 2. Registration with Custom Data
```java
@BeforeMethod
public void setupWithCustomData() {
    RegisterDetails customUser = new RegisterDetails();
    customUser.setFirstName("John");
    customUser.setLastName("Doe");
    customUser.setEmail("john.doe@example.com");
    customUser.setTelephone("1234567890");
    customUser.setPassword("Password123");
    customUser.setConfirmPassword("Password123");
    customUser.setSubscribe("Yes");

    UserRegistrationHelper.registerUserWithoutSavingData(driver, customUser);
}
```

#### 3. Navigate to Register Page Only
```java
@Test
public void testRegisterPageElements() {
    RegisterPageObject registerPage = UserRegistrationHelper.navigateToRegisterPage(driver);
    String title = registerPage.getRegisterAccountTitle();
    Assert.assertEquals(title, "Register Account");
}
```

**Use Cases:**
- **Login Tests**: Register user before testing login functionality
- **Forgot Password Tests**: Create user to test forgot password workflow
- **Account Tests**: Test account-related features with a fresh user
- **Integration Tests**: Create dependencies without file-based data

**Benefits:**
- ✅ Tests are independent and can run in any order
- ✅ No file dependencies (no .txt files needed)
- ✅ Can delete .txt files without breaking tests
- ✅ Reusable across different test classes
- ✅ Clean and maintainable code

## Reporting

- **TestNG Reports**: Generated in `target/surefire-reports/`
- **Screenshots**: Automatically captured on test failures and saved in `Screenshots/` directory
- **Console Output**: Test execution details printed to console

## Contributing

1. Follow the existing code style and patterns
2. Add appropriate comments using single-line comments for steps
3. Ensure tests are independent and can run in parallel
4. Update this README if you add new features or change structure

## Best Practices Implemented

- **DRY (Don't Repeat Yourself)**: Common actions abstracted into utility classes
- **KISS (Keep It Simple Stupid)**: Simple, readable code structure
- **Page Object Model**: Separation of test logic from page-specific code
- **Explicit Waits**: Robust element waiting mechanisms
- **Data-Driven Testing**: Test data separated from test logic
- **Screenshot on Failure**: Easy debugging of failed tests
- **Test Independence**: Tests can run independently without external dependencies
- **Environment Configuration**: Browser and URL settings via `.env` file
- **Factory Pattern**: Data generation using factory classes

## Troubleshooting

- **Driver Issues**: Ensure Chrome browser is updated and compatible
- **Element Not Found**: Check if locators are still valid on the test site
- **Test Failures**: Review screenshots in `Screenshots/` directory
- **Build Issues**: Run `mvn clean install` to resolve dependencies
- **Missing .env file**: Create `.env` file with `BROWSER` and `BASE_URL` variables
- **Test Dependencies**: Ensure you're using `UserRegistrationHelper` for independent tests

## Key Design Decisions

### 1. Environment-Based Configuration
The framework uses a `.env` file instead of hardcoding values, making it easy to:
- Switch browsers without code changes
- Change test URLs for different environments
- Support CI/CD pipeline integration

### 2. Test Independence
By using `UserRegistrationHelper`, tests don't depend on:
- Other test cases running first
- External files or databases
- Manual setup or data preparation

### 3. Data Factories vs. Hard-coded Data
Data factories automatically generate valid random data, ensuring:
- Tests are unique and not detecting same-bug patterns
- Data is always valid for the test scenarios
- Easy to create custom data for edge cases

For any questions or issues, please refer to the code comments or examine the existing test implementations.
