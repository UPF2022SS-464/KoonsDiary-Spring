package UPF2022SS.KoonsDiarySpring.service.diary;

import UPF2022SS.KoonsDiarySpring.api.dto.DefaultResponse;
import UPF2022SS.KoonsDiarySpring.api.dto.diary.PostDiaryImageRequest;
import UPF2022SS.KoonsDiarySpring.domain.Diary;
import UPF2022SS.KoonsDiarySpring.domain.DiaryImage;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public interface DiaryImageService {
    public DiaryImage saveImage(String fileName, String comment);

    public DefaultResponse setImageId(DiaryImage diaryImage, Diary diary);

    public DefaultResponse getImage(Long diaryId);
}
