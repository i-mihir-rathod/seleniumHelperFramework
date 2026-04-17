package dataFactories;

import dataObjects.ForgotPasswordDetails;
import dataObjects.RegisterDetails;
import utils.RandomDataGenerator;

public class ForgotPasswordDataFactory {

    public static ForgotPasswordDetails validEmail(RegisterDetails registerUser) {
        var forgotPassword = new ForgotPasswordDetails();

        forgotPassword.setEmail(registerUser.getEmail());
        return forgotPassword;
    }

    public static ForgotPasswordDetails notRegisteredUser() {
        var forgotPassword = new ForgotPasswordDetails();

        forgotPassword.setEmail(RandomDataGenerator.generateRandomEmail());
        return forgotPassword;
    }

    public static ForgotPasswordDetails invalidUser(ForgotPasswordDetails forgotPasswordDetails) {
        var forgotPassword = new ForgotPasswordDetails();

        forgotPassword.setEmail("m" + forgotPasswordDetails.getEmail() + "m");
        return forgotPassword;
    }
}
