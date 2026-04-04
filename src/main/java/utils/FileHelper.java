package utils;

public class FileHelper {

    public static String getUploadFilePath(String fileName) {
        return PathHelper.getUploadFileRoot() + fileName;
    }
}
