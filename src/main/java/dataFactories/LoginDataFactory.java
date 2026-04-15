package dataFactories;

import dataObjects.LoginDetails;
import dataObjects.RegisterDetails;
import utils.RandomDataGenerator;

public class LoginDataFactory {

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

    public static LoginDetails invalidPassword(LoginDetails loginUserDetails) {
        var loginDetails  = new LoginDetails();
        loginDetails.setEmail(loginUserDetails.getEmail());
        loginDetails.setPassword(RandomDataGenerator.generateRandomPassword());

        return loginDetails;
    }

    public static LoginDetails invalidEmail(LoginDetails loginUserDetails) {
        var loginDetails  = new LoginDetails();
        loginDetails.setEmail(RandomDataGenerator.generateRandomEmail());
        loginDetails.setPassword(loginUserDetails.getPassword());

        return loginDetails;
    }
}
