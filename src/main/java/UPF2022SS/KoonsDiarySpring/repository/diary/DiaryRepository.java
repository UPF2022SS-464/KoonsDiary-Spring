package UPF2022SS.KoonsDiarySpring.repository.diary;

import UPF2022SS.KoonsDiarySpring.api.dto.diary.Emotion;
import UPF2022SS.KoonsDiarySpring.api.dto.diary.MonthlyDiary;
import UPF2022SS.KoonsDiarySpring.domain.Diary;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DiaryRepository {
    public List<Diary> findAllById(Long userId);
    public List<Emotion> findEmotionListByLocalDate(Long userId, LocalDate startDate, LocalDate endDate);
    public Diary findByLocalDate(Long userId, LocalDate localDate);
    public List<MonthlyDiary> findListByMonth(Long uesrId, LocalDate startDate, LocalDate endDate);
//    public List<Diary> findAllByUser(User user);
}
