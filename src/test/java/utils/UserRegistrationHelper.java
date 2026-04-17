package utils;

import dataObjects.RegisterDetails;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import pageObjectsModels.RegisterPageObject;

public class UserRegistrationHelper {

    public static RegisterDetails registerUserWithoutSavingData(WebDriver driver, RegisterDetails user) {
        // initialized page object
        var registerPage = new RegisterPageObject(driver);

        // fill the register form
        registerPage.fillRegisterForm(user);
        registerPage.agreePrivacyPolicy();
        registerPage.clickOnContinueBtn();

        // verify success registered message
        Assert.assertEquals(registerPage.getSuccessRegisteredMsg(), "Your Account Has Been Created!", "User registration failed");

        driver.quit();

        return user;
    }
}

