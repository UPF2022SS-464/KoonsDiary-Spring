package UPF2022SS.KoonsDiarySpring.common;

import org.springframework.http.ContentDisposition;

import java.nio.charset.StandardCharsets;

public class CommonUtils {
    private static final String FILE_EXTENSION_SEPARATOR = ".";


    private static final String CATEGORY_PREFIX = "/";
    private static final String TIME_SEPARATOR = "_";
    private static final int UNDER_BAR_INDEX = 1;


    public static String buildFileName(String username, String originalFileName){

        int fileExtensionIndex = originalFileName.lastIndexOf(FILE_EXTENSION_SEPARATOR);
        String fileExtension = originalFileName.substring(fileExtensionIndex);
        String fileName = originalFileName.substring(0, fileExtensionIndex);
        String now = String.valueOf(System.currentTimeMillis());

        return username + fileName + now + fileExtension;
    }

    public static ContentDisposition createContentDisposition(String imagePath){
        String fileName = imagePath.substring(imagePath.lastIndexOf(CATEGORY_PREFIX)+ UNDER_BAR_INDEX);
        return ContentDisposition.builder("attachment")
                .filename(fileName, StandardCharsets.UTF_8)
                .build();
    }
}
