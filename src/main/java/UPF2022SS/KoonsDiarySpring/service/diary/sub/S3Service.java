package UPF2022SS.KoonsDiarySpring.service.diary.sub;

import UPF2022SS.KoonsDiarySpring.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface S3Service {

    String uploadFile(MultipartFile multipartFile, User user);
    public byte[] downloadFile(String imagePath);
    String getFileUrl(String fileName);

}
