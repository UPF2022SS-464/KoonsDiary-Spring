package UPF2022SS4.KoonsDiarySpring.repository.diary;


import UPF2022SS4.KoonsDiarySpring.domain.Diary;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaryJpaRepository extends JpaRepository<Diary, Long>, DiaryRepository {
}
