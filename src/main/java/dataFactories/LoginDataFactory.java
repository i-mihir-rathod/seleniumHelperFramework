package dataFactories;

import dataObjects.LoginDetails;
import utils.RandomDataGenerator;
import utils.UserDataManager;

public class LoginDataFactory {

    public static LoginDetails validLoginDetails() {
        var loginDetails = new LoginDetails();
        loginDetails.setEmail(UserDataManager.getUser().getEmail());
        loginDetails.setPassword(UserDataManager.getUser().getEmail());

        return loginDetails;
    }

    public static LoginDetails notRegisteredUser() {
        var loginDetails = new LoginDetails();
        loginDetails.setEmail(RandomDataGenerator.generateRandomEmail());
        loginDetails.setPassword(RandomDataGenerator.generateRandomPassword());

        return loginDetails;
    }

    public static LoginDetails invalidPassword() {
        var loginDetails = new LoginDetails();
        loginDetails.setEmail(UserDataManager.getUser().getEmail());
        loginDetails.setPassword(RandomDataGenerator.generateRandomPassword());

        return loginDetails;
    }
}
