package UPF2022SS.KoonsDiarySpring.repository.diary;

import UPF2022SS.KoonsDiarySpring.api.dto.diary.MonthlyDiary;
import UPF2022SS.KoonsDiarySpring.domain.Diary;
import UPF2022SS.KoonsDiarySpring.domain.User;
import com.querydsl.core.Tuple;
import org.apache.tomcat.jni.Local;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DiaryRepository {
    public List<Diary> findAllById(Long userId);
    public List<Diary> findListByLocalDate(Long userId, LocalDate startDate, LocalDate endDate);
    public Diary findByLocalDate(Long userId, LocalDate localDate);
    public List<MonthlyDiary> findListByMonth(Long uesrId, LocalDate startDate, LocalDate endDate);
//    public List<Diary> findAllByUser(User user);
}
