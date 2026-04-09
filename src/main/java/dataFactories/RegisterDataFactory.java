package dataFactories;

import dataObjects.RegisterDetails;
import utils.RandomDataGenerator;

public class RegisterDataFactory {

    public static RegisterDetails blankData() {
        return new RegisterDetails();
    }

    public static RegisterDetails validData() {
        var registerData = new RegisterDetails();

        registerData.setFirstName(RandomDataGenerator.generateRandomFirstName());
        registerData.setLastName(RandomDataGenerator.generateRandomLastName());
        registerData.setEmail(RandomDataGenerator.generateRandomEmail());
        registerData.setTelephone(RandomDataGenerator.generateRandomPhoneNumber());
        registerData.setPassword(RandomDataGenerator.generateRandomPassword());
        registerData.setConfirmPassword(registerData.getPassword());
        registerData.setSubscribe("Yes");

        return registerData;
    }

    public static RegisterDetails passwordMismatched() {
        var registerData = new RegisterDetails();

        registerData.setFirstName(RandomDataGenerator.generateRandomFirstName());
        registerData.setLastName(RandomDataGenerator.generateRandomLastName());
        registerData.setEmail(RandomDataGenerator.generateRandomEmail());
        registerData.setTelephone(RandomDataGenerator.generateRandomPhoneNumber());
        registerData.setPassword(RandomDataGenerator.generateRandomPassword());
        registerData.setConfirmPassword(RandomDataGenerator.generateRandomPassword());
        registerData.setSubscribe("yes");

        return registerData;
    }

    public static RegisterDetails alphabeticalValuesInPhoneNumber() {
        var registerData = new RegisterDetails();

        registerData.setFirstName(RandomDataGenerator.generateRandomFirstName());
        registerData.setLastName(RandomDataGenerator.generateRandomLastName());
        registerData.setEmail(RandomDataGenerator.generateRandomEmail());
        registerData.setTelephone(RandomDataGenerator.generateRandomAlphabetical());
        registerData.setPassword(RandomDataGenerator.generateRandomPassword());
        registerData.setConfirmPassword(registerData.getPassword());
        registerData.setSubscribe("yes");

        return registerData;
    }
}
