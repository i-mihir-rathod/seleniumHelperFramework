package dataFactories;

import dataObjects.LoginDetails;
import dataObjects.RegisterDetails;
import utils.RandomDataGenerator;
import utils.UserDataManager;

public class LoginDataFactory {

    static RegisterDetails registerData = new RegisterDetails();

    public static LoginDetails validLoginDetails(RegisterDetails registerUserDetails) {
        var loginDetails  = new LoginDetails();
        loginDetails.setEmail(registerUserDetails.getEmail());
        loginDetails.setPassword(registerUserDetails.getPassword());

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
