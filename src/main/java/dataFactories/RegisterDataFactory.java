package dataFactories;

import dataObjects.RegisterPageDataObject;
import java.util.Random;
import java.util.UUID;

public class RegisterDataFactory {

    public static RegisterPageDataObject blankData() {
        return new RegisterPageDataObject();
    }

    public static RegisterPageDataObject validData() {
        var registerData = new RegisterPageDataObject();
        String uniqueId = UUID.randomUUID().toString().substring(0, 5);

        registerData.setFirstName("Test" + uniqueId);
        registerData.setLastName("User" + uniqueId);
        registerData.setEmail("test" + uniqueId + "@test.com");
        registerData.setTelephone(generatePhoneNumber());
        registerData.setPassword("1234");
        registerData.setConfirmPassword(registerData.getPassword());
        registerData.setSubscribe("yes");

        return registerData;
    }

    public static RegisterPageDataObject passwordMismatched() {
        var registerData = new RegisterPageDataObject();
        String uniqueId = UUID.randomUUID().toString().substring(0, 5);

        registerData.setFirstName("Test" + uniqueId);
        registerData.setLastName("User" + uniqueId);
        registerData.setEmail("test" + uniqueId + "@test.com");
        registerData.setTelephone(generatePhoneNumber());
        registerData.setPassword(uniqueId);
        registerData.setConfirmPassword(uniqueId + "mismatch");
        registerData.setSubscribe("yes");

        return registerData;
    }

    public static RegisterPageDataObject alphabeticalValuesInPhoneNumber() {
        var registerData = new RegisterPageDataObject();
        String uniqueId = UUID.randomUUID().toString().substring(0, 5);

        registerData.setFirstName("Test" + uniqueId);
        registerData.setLastName("User" + uniqueId);
        registerData.setEmail("test" + uniqueId + "@test.com");
        registerData.setTelephone(uniqueId);
        registerData.setPassword(uniqueId);
        registerData.setConfirmPassword(uniqueId);
        registerData.setSubscribe("yes");

        return registerData;
    }

    private static String generatePhoneNumber() {
        Random random = new Random();
        StringBuilder phone = new StringBuilder("9"); //  start with 9 (valid Indian style)

        for (int i = 0; i < 9; i++) {
            phone.append(random.nextInt(10)); // append 9 random digits
        }

        return phone.toString();
    }
}
