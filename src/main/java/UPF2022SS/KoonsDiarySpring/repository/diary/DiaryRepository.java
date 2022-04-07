package UPF2022SS.KoonsDiarySpring.repository.diary;

import UPF2022SS.KoonsDiarySpring.domain.Diary;
import UPF2022SS.KoonsDiarySpring.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiaryRepository {

    public void saveDiary(Diary diary, User user);
    public List<Diary> findAllById(Long userId);
    public List<Diary> findAllByUser(User user);
//    public Diary findById(Long id);


}
