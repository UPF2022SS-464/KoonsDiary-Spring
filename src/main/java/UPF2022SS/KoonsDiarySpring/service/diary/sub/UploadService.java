package UPF2022SS.KoonsDiarySpring.service.diary.sub;

import UPF2022SS.KoonsDiarySpring.domain.User;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface UploadService {

    String uploadFile(MultipartFile multipartFile, User user);

    String getFileUrl(String fileName);

}
