# Selenium Automation Framework for OpenCart

This is a robust Selenium WebDriver-based automation framework designed for testing the OpenCart e-commerce platform. The framework follows the Page Object Model (POM) design pattern and incorporates best practices for maintainable and scalable test automation.

## Table of Contents
- [Overview](#overview)
- [Architecture](#architecture)
- [Prerequisites](#prerequisites)
- [Setup](#setup)
- [Running Tests](#running-tests)
- [Project Structure](#project-structure)
- [Writing New Tests](#writing-new-tests)
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
To ensure tests can run independently without external dependencies (e.g., files or prior test execution), the framework includes setup helpers. For example, login tests use a register helper to create users on-the-fly without saving data to files, allowing login tests to execute standalone.

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
├── pom.xml                           # Maven configuration
├── README.md                         # Project documentation
├── src/
│   ├── main/java/
│   │   ├── dataFactories/            # Test data generation
│   │   │   ├── LoginDataFactory.java
│   │   │   ├── ProductDataFactory.java
│   │   │   └── RegisterDataFactory.java
│   │   ├── dataObjects/              # Data transfer objects
│   │   │   ├── LoginDetails.java
│   │   │   ├── ProductDetails.java
│   │   │   └── RegisterPageDataObject.java
│   │   ├── pageObjectsModels/        # Page Object classes
│   │   │   ├── BasePageObject.java
│   │   │   ├── HomePageObject.java
│   │   │   ├── LoginPageObject.java
│   │   │   ├── RegisterPageObject.java
│   │   │   ├── SearchPageObject.java
│   │   │   └── ShoppingCartPageObject.java
│   │   └── utils/                    # Utility classes
│   │       ├── FilePathHelper.java
│   │       ├── FluentWaitUtils.java
│   │       ├── JavaScriptHelper.java
│   │       ├── RandomDataGenerator.java
│   │       ├── SeleniumHelper.java
│   │       ├── UserDataManager.java
│   │       └── WaitUtils.java
│   └── test/java/
│       ├── Base.java                 # Base test class
│       ├── CartPageTestCases.java    # Cart page tests
│       ├── homePageTestCases.java    # Home page tests
│       ├── LoginPageTestCases.java   # Login page tests
│       ├── RegisterPageTestCases.java # Registration tests
│       └── utils/                    # Test utilities
│           └── DriverFactory.java    # WebDriver factory
├── target/                           # Compiled classes and test reports
├── Screenshots/                      # Failure screenshots
├── memoryBox/                        # Temporary storage for test artifacts
└── uploadFiles/                      # Test files for upload tests
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

## Troubleshooting

- **Driver Issues**: Ensure Chrome browser is updated and compatible
- **Element Not Found**: Check if locators are still valid on the test site
- **Test Failures**: Review screenshots in `Screenshots/` directory
- **Build Issues**: Run `mvn clean install` to resolve dependencies

For any questions or issues, please refer to the code comments or examine the existing test implementations.
