package UPF2022SS.KoonsDiarySpring.common;

public class CommonUtils {
    private static final String FILE_EXTENSION_SEPARATOR = ".";

    public static String buildFileName(String username, String originalFileName){

        int fileExtensionIndex = originalFileName.lastIndexOf(FILE_EXTENSION_SEPARATOR);
        String fileExtension = originalFileName.substring(fileExtensionIndex);
        String fileName = originalFileName.substring(0, fileExtensionIndex);
        String now = String.valueOf(System.currentTimeMillis());

        return username + fileName + now + fileExtension;
    }
}
