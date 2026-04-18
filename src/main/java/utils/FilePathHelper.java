package utils;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * FilePathHelper - Utility class for managing file paths across the framework
 * Uses Java NIO Path API for cross-platform compatibility
 */
public class FilePathHelper {

    private static final String PROJECT_ROOT = System.getProperty("user.dir");

    /**
     * Gets the path for upload files directory
     * @param fileName - Name of the file to upload
     * @return Absolute path to the upload file
     */
    public static String getUploadFilePath(String fileName) {
        return Paths.get(PROJECT_ROOT, "uploadFiles", fileName).toString();
    }

    /**
     * Gets the path for user data files (e.g., registered user credentials)
     * @param fileName - Name of the user data file
     * @return Absolute path to the user data file
     */
    public static String getUserDataPath(String fileName) {
        return Paths.get(PROJECT_ROOT, "memoryBox", fileName).toString();
    }

    /**
     * Gets the path for failed test case screenshots
     * @param testName - Name of the test case
     * @return Absolute path to the screenshot file with timestamp
     */
    public static String getFailedCaseScreenShotPath(String testName) {
        return Paths.get(PROJECT_ROOT, "Screenshots", testName + "_" + System.currentTimeMillis() + ".png").toString();
    }
}
