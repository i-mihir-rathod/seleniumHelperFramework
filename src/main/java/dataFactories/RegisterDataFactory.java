package dataFactories;

import dataObjects.RegisterPageDataObject;
import utils.RandomDataGenerator;

public class RegisterDataFactory {

    public static RegisterPageDataObject blankData() {
        return new RegisterPageDataObject();
    }

    public static RegisterPageDataObject validData() {
        var registerData = new RegisterPageDataObject();

        registerData.setFirstName(RandomDataGenerator.generateRandomFirstName());
        registerData.setLastName(RandomDataGenerator.generateRandomLastName());
        registerData.setEmail(RandomDataGenerator.generateRandomEmail());
        registerData.setTelephone(RandomDataGenerator.generateRandomPhoneNumber());
        registerData.setPassword(RandomDataGenerator.generateRandomPassword());
        registerData.setConfirmPassword(registerData.getPassword());
        registerData.setSubscribe("Yes");

        return registerData;
    }

    public static RegisterPageDataObject passwordMismatched() {
        var registerData = new RegisterPageDataObject();

        registerData.setFirstName(RandomDataGenerator.generateRandomFirstName());
        registerData.setLastName(RandomDataGenerator.generateRandomLastName());
        registerData.setEmail(RandomDataGenerator.generateRandomEmail());
        registerData.setTelephone(RandomDataGenerator.generateRandomPhoneNumber());
        registerData.setPassword(RandomDataGenerator.generateRandomPassword());
        registerData.setConfirmPassword(RandomDataGenerator.generateRandomPassword());
        registerData.setSubscribe("yes");

        return registerData;
    }

    public static RegisterPageDataObject alphabeticalValuesInPhoneNumber() {
        var registerData = new RegisterPageDataObject();

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
