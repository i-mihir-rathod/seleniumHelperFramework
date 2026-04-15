# Framework Architecture Diagram

## 1. WebDriver Initialization Flow

```
┌─────────────────────────────────────────────────────────────────┐
│                      Test Execution Starts                       │
└───────────────────────────┬─────────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────────────┐
│  Base.java @BeforeSuite                                         │
│  ├─ Load .env file using dotenv                                │
│  ├─ Read BROWSER = "chrome/firefox/edge"                       │
│  └─ Read BASE_URL = "https://..."                              │
└───────────────────────────┬─────────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────────────┐
│  Base.java @BeforeMethod (runs before EACH test)               │
│  ├─ Call DriverFactory.getDriver(browserName)                  │
│  └─ driver.get(baseUrl)                                        │
└───────────────────────────┬─────────────────────────────────────┘
                            │
                            ▼
                ┌───────────┴───────────┐
                │                       │
        ┌───────▼────────┐   ┌──────────▼──────────┐
        │   Chrome?      │   │  Firefox?  Edge?   │
        │   YES ──►      │   │  YES ──►           │
        └────────────────┘   └────────────────────┘
                │                      │
                ▼                      ▼
        ┌──────────────┐        ┌─────────────┐
        │ ChromeDriver │        │ FirefoxDriver
        │ + Options    │        │ + Options   │
        └──────────────┘        └─────────────┘
                │                      │
                └──────────┬───────────┘
                           │
                           ▼
        ┌──────────────────────────────────┐
        │ maximize window                  │
        │ return WebDriver instance        │
        └──────────────────────────────────┘
                           │
                           ▼
        ┌──────────────────────────────────┐
        │  Test Method @Test Executes      │
        └──────────────────────────────────┘
                           │
                           ▼
┌─────────────────────────────────────────────────────────────────┐
│  Base.java @AfterMethod                                         │
│  ├─ Check if test passed or failed                             │
│  ├─ If FAILED: Take screenshot and save                        │
│  └─ driver.quit()                                              │
└─────────────────────────────────────────────────────────────────┘
```

## 2. Test Setup with UserRegistrationHelper

```
┌─────────────────────────────────────────────────────────────────┐
│  LoginPageTestCases extends Base                                │
│  ├─ @BeforeMethod registerUserBeforeTest()                    │
│  │  └─ Call UserRegistrationHelper.registerUserWithoutSavingData()
│  └─ @Test method                                              │
└───────────────────────────┬─────────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────────────┐
│  UserRegistrationHelper.registerUserWithoutSavingData(driver)   │
│                                                                 │
│  Step 1: Generate Random Valid User Data                       │
│  ├─ Call RegisterDataFactory.validData()                       │
│  └─ Returns RegisterDetails with random:                       │
│     ├─ firstName, lastName (random names)                      │
│     ├─ email (random unique email)                             │
│     ├─ telephone (random valid phone)                          │
│     ├─ password (random secure password)                       │
│     └─ confirmPassword (matches password)                      │
└───────────────────────────┬─────────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────────────┐
│  Navigate to Register Page                                      │
│  ├─ Initialize HomePageObject(driver)                          │
│  ├─ Initialize RegisterPageObject(driver)                      │
│  └─ homePage.navigateToRegister()                              │
└───────────────────────────┬─────────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────────────┐
│  Fill Registration Form                                         │
│  ├─ enterFirstName(user.getFirstName())                        │
│  ├─ enterLastName(user.getLastName())                          │
│  ├─ enterEmail(user.getEmail())                                │
│  ├─ enterTelephone(user.getTelephone())                        │
│  ├─ enterPassword(user.getPassword())                          │
│  └─ enterConfirmPassword(user.getConfirmPassword())            │
└───────────────────────────┬─────────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────────────┐
│  Agree to Privacy Policy                                        │
│  └─ registerPage.agreePrivacyPolicy()                          │
└───────────────────────────┬─────────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────────────┐
│  Click Continue Button                                          │
│  └─ registerPage.clickOnContinueBtn()                          │
└───────────────────────────┬─────────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────────────┐
│  Return RegisterDetails Object                                  │
│  ├─ Contains: email, password, firstName, etc.                 │
│  └─ Ready to use for login test                                │
└───────────────────────────┬─────────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────────────┐
│  Convert to LoginDetails (in @BeforeMethod)                    │
│  ├─ Call LoginDataFactory.validLoginDetails(user)              │
│  └─ Returns LoginDetails with:                                 │
│     ├─ email (from RegisterDetails)                            │
│     └─ password (from RegisterDetails)                         │
└───────────────────────────┬─────────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────────────┐
│  @Test Method Now Executes                                      │
│  ├─ Test has fresh user (just registered)                     │
│  ├─ No file dependencies                                       │
│  ├─ Each test gets its own unique user                         │
│  └─ Tests are truly independent                                │
└─────────────────────────────────────────────────────────────────┘
```

## 3. Data Flow - Before vs After

### BEFORE (File-based Dependencies)
```
RegisterPageTestCases
       │
       ├─► Registers user
       │
       ├─► SavesTo user.txt
       │
       └─► Creates file
                │
                ▼
        user.txt (File created)
                │
                ▼
    LoginPageTestCases (DEPENDS on file)
        │
        ├─► Reads user.txt
        │
        ├─► Gets email & password
        │
        └─► Runs test
        
    Problem: If file is deleted ─► Login tests FAIL! ❌
```

### AFTER (Helper-based Independence)
```
LoginPageTestCases
       │
       ├─► @BeforeMethod runs
       │
       ├─► Calls UserRegistrationHelper
       │
       ├─► Generates random valid user
       │
       ├─► Registers user via UI
       │
       ├─► Gets RegisterDetails object
       │
       └─► Runs test with fresh user
       
No files involved ─► Tests are independent! ✅
Can run in any order ─► Parallel execution ready! ✅
```

## 4. Component Interaction Diagram

```
┌──────────────────────────────────────────────────────────────┐
│                    TEST CLASSES                              │
│  ┌─────────────────┐  ┌─────────────────┐                   │
│  │ LoginPageTests  │  │ ForgotPwdTests  │                   │
│  │ (Independent)   │  │ (Independent)   │                   │
│  └────────┬────────┘  └────────┬────────┘                   │
│           │                    │                            │
│           └────────┬───────────┘                            │
│                    │                                        │
└────────────────────┼────────────────────────────────────────┘
                     │
                     ▼
┌──────────────────────────────────────────────────────────────┐
│              UTILITY LAYER (src/main/java/utils)             │
│  ┌──────────────────────────────────────────────────────┐   │
│  │  UserRegistrationHelper                             │   │
│  │  ├─ registerUserWithoutSavingData(driver)           │   │
│  │  ├─ registerUserWithoutSavingData(driver, user)     │   │
│  │  └─ navigateToRegisterPage(driver)                  │   │
│  └──────────────────┬───────────────────────────────────┘   │
│                     │                                        │
│  ┌──────────────────┼───────────────────────────────────┐   │
│  │  DATA FACTORIES                                       │   │
│  │  ├─ RegisterDataFactory.validData()                 │   │
│  │  └─ LoginDataFactory.validLoginDetails()            │   │
│  └──────────────────┼───────────────────────────────────┘   │
│                     │                                        │
│  ┌──────────────────┼───────────────────────────────────┐   │
│  │  DATA OBJECTS                                        │   │
│  │  ├─ RegisterDetails                                 │   │
│  │  └─ LoginDetails                                    │   │
│  └──────────────────┼───────────────────────────────────┘   │
│                     │                                        │
└─────────────────────┼────────────────────────────────────────┘
                      │
                      ▼
┌──────────────────────────────────────────────────────────────┐
│            PAGE OBJECT MODELS (pageObjectsModels)            │
│  ┌────────────────┐  ┌────────────────┐                     │
│  │ RegisterPage   │  │  LoginPage     │                     │
│  │ Object         │  │  Object        │                     │
│  └────────────────┘  └────────────────┘                     │
│  ├─ fillRegisterForm()                                      │
│  ├─ agreePrivacyPolicy()              ├─ fillLoginForm()   │
│  ├─ clickOnContinueBtn()               ├─ clickOnLoginBtn()│
│  └─ getErrorMessages()                 └─ getWarningMsg()  │
└──────────────────────────────────────────────────────────────┘
                      │
                      ▼
┌──────────────────────────────────────────────────────────────┐
│          SELENIUM HELPER (utils/SeleniumHelper)              │
│  ├─ clickOnElement(By)                                      │
│  ├─ enterText(By, String)                                   │
│  ├─ getText(By)                                             │
│  ├─ isElementDisplayed(By)                                  │
│  └─ All element interaction methods                         │
└──────────────────────────────────────────────────────────────┘
                      │
                      ▼
┌──────────────────────────────────────────────────────────────┐
│          WEBDRIVER (Chrome/Firefox/Edge)                     │
│  ├─ Initialized by DriverFactory                            │
│  ├─ Configured with appropriate options                     │
│  └─ Managed by Base class                                   │
└──────────────────────────────────────────────────────────────┘
```

## 5. File Dependencies

```
LoginPageTestCases.java
    │
    ├─► imports Base.java (extends)
    │
    ├─► imports UserRegistrationHelper (utility)
    │    │
    │    ├─► imports RegisterDataFactory
    │    │
    │    ├─► imports HomePageObject
    │    │
    │    └─► imports RegisterPageObject
    │
    ├─► imports LoginDataFactory
    │
    ├─► imports HomePageObject
    │
    └─► imports LoginPageObject

Base.java
    │
    ├─► imports DriverFactory (browser initialization)
    │
    ├─► imports dotenv (environment variables)
    │
    └─► imports FilePathHelper (screenshot paths)

.env file (Root Directory)
    │
    ├─ BROWSER=chrome (or firefox, edge)
    │
    └─ BASE_URL=https://...
```

## 6. Test Execution Timeline

```
Time │ Event
────┼──────────────────────────────────────────────────────────
  0  │ Test Suite Starts
     │
  1  │ @BeforeSuite - Load .env variables
     │  └─ BROWSER = "chrome"
     │  └─ BASE_URL = "https://..."
     │
  2  │ Test 1: loginWithValidUser
     │  ├─ @BeforeMethod
     │  │  ├─ DriverFactory.getDriver("chrome")
     │  │  │  └─ Initialize ChromeDriver
     │  │  ├─ driver.get(BASE_URL)
     │  │  │  └─ Navigate to homepage
     │  │  └─ registerUserBeforeTest()
     │  │     ├─ UserRegistrationHelper.registerUserWithoutSavingData()
     │  │     │  ├─ Generate random user
     │  │     │  ├─ Register on UI
     │  │     │  └─ Return RegisterDetails
     │  │     └─ Convert to LoginDetails
     │  │
     │  ├─ @Test loginWithValidUser()
     │  │  ├─ Navigate to login page
     │  │  ├─ Enter email & password
     │  │  └─ Verify login success
     │  │
     │  └─ @AfterMethod
     │     ├─ Test passed? Cleanup.
     │     └─ driver.quit()
     │
  3  │ Test 2: loginWithInvalidPassword
     │  ├─ @BeforeMethod
     │  │  ├─ New WebDriver created (fresh session)
     │  │  └─ New user registered (independent!)
     │  │
     │  ├─ @Test loginWithInvalidPassword()
     │  │  ├─ Try login with wrong password
     │  │  └─ Verify error message
     │  │
     │  └─ @AfterMethod
     │     └─ Cleanup
     │
  4  │ Test Suite Ends
```

## Key Benefits Visualization

```
┌──────────────────────────────────────────────────────────┐
│  BEFORE: File-Based Dependencies                         │
│                                                          │
│  RegisterTest ──► user.txt ◄── LoginTest              │
│          (creates)    ↑         (reads)                │
│                       │                                 │
│            Dependency Chain: HIGH COUPLING              │
│            Problem: Delete file ─► Tests Fail! ❌       │
└──────────────────────────────────────────────────────────┘

┌──────────────────────────────────────────────────────────┐
│  AFTER: Helper-Based Independence                        │
│                                                          │
│  LoginTest ──┐                                          │
│             ├─► UserRegistrationHelper                 │
│             └─► Fresh User Created for Each Test       │
│                                                          │
│            No Coupling: LOW DEPENDENCIES                │
│            Benefit: Parallel Execution Ready! ✅         │
└──────────────────────────────────────────────────────────┘
```


