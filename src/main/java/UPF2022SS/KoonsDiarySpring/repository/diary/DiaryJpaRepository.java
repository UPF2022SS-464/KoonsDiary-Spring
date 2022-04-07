package UPF2022SS.KoonsDiarySpring.repository.diary;


import UPF2022SS.KoonsDiarySpring.domain.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaryJpaRepository extends JpaRepository<Diary, Long>, DiaryRepository {
}
