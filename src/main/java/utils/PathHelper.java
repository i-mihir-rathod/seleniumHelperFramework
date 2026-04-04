package utils;

public class PathHelper {
    public static String getUploadFileRoot() {
        return System.getProperty("user.dir") + "/uploadFiles/";
    }
}
