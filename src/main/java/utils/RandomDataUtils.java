package utils;

import java.util.Random;
import java.util.UUID;

public class RandomDataUtils {

    private static final Random random = new Random();

    /**
     * Generates a random first name.
     *
     * @return A random first name string.
     */
    public static String generateRandomFirstName() {
        String[] firstNames = {"John", "Jane", "Alice", "Bob", "Charlie", "Diana", "Eve", "Frank", "Grace", "Henry"};
        return firstNames[random.nextInt(firstNames.length)] + UUID.randomUUID().toString().substring(0, 3);
    }

    public static String generateRandomFirstName(int length) {
        String[] firstNames = {"John", "Jane", "Alice", "Bob", "Charlie", "Diana", "Eve", "Frank", "Grace", "Henry"};
        return firstNames[random.nextInt(firstNames.length)] + UUID.randomUUID().toString().substring(0, length);
    }

    /**
     * Generates a random last name.
     *
     * @return A random last name string.
     */
    public static String generateRandomLastName() {
        String[] lastNames = {"Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis", "Rodriguez", "Martinez"};
        return lastNames[random.nextInt(lastNames.length)] + UUID.randomUUID().toString().substring(0, 3);
    }

    public static String generateRandomLastName(int length) {
        String[] lastNames = {"Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis", "Rodriguez", "Martinez"};
        return lastNames[random.nextInt(lastNames.length)] + UUID.randomUUID().toString().substring(0, length);
    }

    /**
     * Generates a random email address.
     *
     * @return A random email string.
     */
    public static String generateRandomEmail() {
        String uniqueId = UUID.randomUUID().toString().substring(0, 8);
        return "test" + uniqueId + "@test.com";
    }

    public static String generateRandomEmail(int length) {
        String uniqueId = UUID.randomUUID().toString().substring(0, length);
        return "test" + uniqueId + "@test.com";
    }

    /**
     * Generates a random phone number (Indian style, starting with 9).
     *
     * @return A random phone number string.
     */
    public static String generateRandomPhoneNumber() {
        StringBuilder phone = new StringBuilder("9"); // start with 9 (valid Indian style)

        for (int i = 0; i < 9; i++) {
            phone.append(random.nextInt(10)); // append 9 random digits
        }
        return phone.toString();
    }

    public static String generateRandomPhoneNumber(int length) {
        StringBuilder phone = new StringBuilder("9"); // start with 9 (valid Indian style)

        for (int i = 0; i < length; i++) {
            phone.append(random.nextInt(10)); // append 9 random digits
        }
        return phone.toString();
    }

    public static String generateRandomPassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }
        return password.toString();
    }

    public static String generateRandomPassword(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < length; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }
        return password.toString();
    }

    public static String generateRandomAlphabetical() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            result.append(chars.charAt(random.nextInt(chars.length())));
        }
        return result.toString();
    }

    public static String generateRandomAlphabetical(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder result = new StringBuilder();
        for (int i = 1; i < length; i++) {
            result.append(chars.charAt(random.nextInt(chars.length())));
        }
        return result.toString();
    }

    public static String generateRandomAlphanumeric() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            result.append(chars.charAt(random.nextInt(chars.length())));
        }
        return result.toString();
    }

    public static String generateRandomAlphanumeric(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder result = new StringBuilder();
        for (int i = 1; i < length; i++) {
            result.append(chars.charAt(random.nextInt(chars.length())));
        }
        return result.toString();
    }

    /**
     * Generates a random uppercase (capital) letter string.
     *
     * @param length The desired length of the string.
     * @return A random uppercase letter string.
     */
    public static String generateRandomCapitalLetters(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder result = new StringBuilder();
        for (int i = 1; i < length; i++) {
            result.append(chars.charAt(random.nextInt(chars.length())));
        }
        return result.toString();
    }

    public static String generateRandomCapitalLetters() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder result = new StringBuilder();
        for (int i = 1; i < 5; i++) {
            result.append(chars.charAt(random.nextInt(chars.length())));
        }
        return result.toString();
    }

    /**
     * Generates a random lowercase (small) letter string.
     *
     * @param length The desired length of the string.
     * @return A random lowercase letter string.
     */
    public static String generateRandomSmallLetters(int length) {
        String chars = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder result = new StringBuilder();
        for (int i = 1; i < length; i++) {
            result.append(chars.charAt(random.nextInt(chars.length())));
        }
        return result.toString();
    }

    public static String generateRandomSmallLetters() {
        String chars = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder result = new StringBuilder();
        for (int i = 1; i < 5; i++) {
            result.append(chars.charAt(random.nextInt(chars.length())));
        }
        return result.toString();
    }

    /**
     * Generates a random numeric string (numbers only).
     *
     * @param length The desired length of the string.
     * @return A random numeric string.
     */
    public static String generateRandomNumeric(int length) {
        String chars = "0123456789";
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            result.append(chars.charAt(random.nextInt(chars.length())));
        }
        return result.toString();
    }

    public static String generateRandomNumeric() {
        String chars = "0123456789";
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            result.append(chars.charAt(random.nextInt(chars.length())));
        }
        return result.toString();
    }

    /**
     * Generates a random alphanumeric string with special characters.
     *
     * @param length The desired length of the string.
     * @return A random alphanumeric string with special characters.
     */
    public static String generateRandomAlphanumericWithSpecial(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*_-+=";
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            result.append(chars.charAt(random.nextInt(chars.length())));
        }
        return result.toString();
    }

    public static String generateRandomAlphanumericWithSpecial() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*_-+=";
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            result.append(chars.charAt(random.nextInt(chars.length())));
        }
        return result.toString();
    }
}
