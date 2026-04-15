package utils;

import dataObjects.RegisterDetails;
import org.openqa.selenium.WebDriver;
import pageObjectsModels.RegisterPageObject;

public class UserRegistrationHelper {

    public static RegisterDetails registerUserWithoutSavingData(WebDriver driver, RegisterDetails user) {
        // initialized page object
        var registerPage = new RegisterPageObject(driver);

        // fill the register form
        registerPage.fillRegisterForm(user);
        registerPage.agreePrivacyPolicy();
        registerPage.clickOnContinueBtn();

        driver.quit();

        return user;
    }
}

