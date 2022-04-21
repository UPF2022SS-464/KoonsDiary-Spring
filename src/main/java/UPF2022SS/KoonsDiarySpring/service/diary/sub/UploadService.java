package UPF2022SS.KoonsDiarySpring.service.diary.sub;

import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface UploadService {

    String uploadFile(MultipartFile multipartFile, String userId);

    String getFileUrl(String fileName);

}
