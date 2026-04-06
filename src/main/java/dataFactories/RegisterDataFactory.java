package dataFactories;

import dataObjects.RegisterPageDataObject;
import utils.RandomDataUtils;

public class RegisterDataFactory {

    public static RegisterPageDataObject blankData() {
        return new RegisterPageDataObject();
    }

    public static RegisterPageDataObject validData() {
        var registerData = new RegisterPageDataObject();

        registerData.setFirstName(RandomDataUtils.generateRandomFirstName());
        registerData.setLastName(RandomDataUtils.generateRandomLastName());
        registerData.setEmail(RandomDataUtils.generateRandomEmail());
        registerData.setTelephone(RandomDataUtils.generateRandomPhoneNumber());
        registerData.setPassword(RandomDataUtils.generateRandomPassword());
        registerData.setConfirmPassword(registerData.getPassword());
        registerData.setSubscribe("yes");

        return registerData;
    }

    public static RegisterPageDataObject passwordMismatched() {
        var registerData = new RegisterPageDataObject();

        registerData.setFirstName(RandomDataUtils.generateRandomFirstName());
        registerData.setLastName(RandomDataUtils.generateRandomLastName());
        registerData.setEmail(RandomDataUtils.generateRandomEmail());
        registerData.setTelephone(RandomDataUtils.generateRandomPhoneNumber());
        registerData.setPassword(RandomDataUtils.generateRandomPassword());
        registerData.setConfirmPassword(RandomDataUtils.generateRandomPassword());
        registerData.setSubscribe("yes");

        return registerData;
    }

    public static RegisterPageDataObject alphabeticalValuesInPhoneNumber() {
        var registerData = new RegisterPageDataObject();

        registerData.setFirstName(RandomDataUtils.generateRandomFirstName());
        registerData.setLastName(RandomDataUtils.generateRandomLastName());
        registerData.setEmail(RandomDataUtils.generateRandomEmail());
        registerData.setTelephone(RandomDataUtils.generateRandomAlphabetical());
        registerData.setPassword(RandomDataUtils.generateRandomPassword());
        registerData.setConfirmPassword(RandomDataUtils.generateRandomPassword());
        registerData.setSubscribe("yes");

        return registerData;
    }
}
