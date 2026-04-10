package utils;

public class FilePathHelper {

    public static String getUploadFilePath(String fileName) {
        return System.getProperty("user.dir") + "/uploadFiles/" + fileName;
    }

    public static String getUserDataPath(String fileName) {
        return System.getProperty("user.dir") + "/memoryBox/" + fileName;
    }

    public static String getFailedCaseScreenShotPath(String testName) {
        return System.getProperty("user.dir") + "/Screenshots/" + testName + "_" + System.currentTimeMillis() + ".png";
    }
}
