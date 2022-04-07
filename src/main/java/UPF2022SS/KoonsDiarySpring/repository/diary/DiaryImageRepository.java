package UPF2022SS.KoonsDiarySpring.repository.diary;

import UPF2022SS.KoonsDiarySpring.domain.DiaryImage;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiaryImageRepository {
    public List<DiaryImage> findByDiaryId(Long id);
}
