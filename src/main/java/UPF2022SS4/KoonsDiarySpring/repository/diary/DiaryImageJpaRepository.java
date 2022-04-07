package UPF2022SS4.KoonsDiarySpring.repository.diary;

import UPF2022SS4.KoonsDiarySpring.domain.DiaryImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaryImageJpaRepository extends JpaRepository<DiaryImage, Long>,DiaryImageRepository {
}
