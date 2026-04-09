package utils;

import dataObjects.RegisterDetails;

import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class UserDataManager {

    private static final String FILE_PATH = FilePathHelper.getUserDataPath("user.txt");

    // Save user data
    public static void saveUser(RegisterDetails user) {
        try {
            FileWriter writer = new FileWriter(FILE_PATH);

            writer.write(user.getEmail() + "\n");
            writer.write(user.getPassword());

            writer.close();
        } catch (Exception e) {
            throw new RuntimeException("Failed to save user data", e);
        }
    }

    // Get user data
    public static RegisterDetails getUser() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(FILE_PATH));

            RegisterDetails user = new RegisterDetails();

            user.setEmail(lines.get(0));
            user.setPassword(lines.get(1));
            user.setConfirmPassword(lines.get(1)); // important for consistency

            return user;

        } catch (Exception e) {
            throw new RuntimeException("Failed to read user data", e);
        }
    }
}
