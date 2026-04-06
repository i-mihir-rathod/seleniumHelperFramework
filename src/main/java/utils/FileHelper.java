package utils;

public class FileHelper {

    public static String getUploadFilePath(String fileName) {
        return System.getProperty("user.dir") + "/uploadFiles/" + fileName;
    }
}
