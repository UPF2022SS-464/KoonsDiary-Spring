package UPF2022SS.KoonsDiarySpring.repository.diary;

import UPF2022SS.KoonsDiarySpring.domain.Diary;
import UPF2022SS.KoonsDiarySpring.domain.User;
import org.apache.tomcat.jni.Local;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DiaryRepository {
    public List<Diary> findAllById(Long userId);
    public List<Diary> findListByLocalDate(Long userId, LocalDate startDate, LocalDate endDate);
    public Diary findByLocalDate(Long userId, LocalDate localDate);
//    public List<Diary> findAllByUser(User user);
}
