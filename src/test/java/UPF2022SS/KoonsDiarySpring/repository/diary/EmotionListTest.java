package UPF2022SS.KoonsDiarySpring.repository.diary;

import UPF2022SS.KoonsDiarySpring.QuerydslConfig;
import UPF2022SS.KoonsDiarySpring.api.dto.diary.Emotion;
import UPF2022SS.KoonsDiarySpring.domain.Diary;
import UPF2022SS.KoonsDiarySpring.domain.User;
import UPF2022SS.KoonsDiarySpring.repository.user.UserJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(QuerydslConfig.class)
@ExtendWith(SpringExtension.class)
class EmotionListTest {

    @Autowired
    private DiaryJpaRepository diaryJpaRepository;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Test
    void findEmotionListByLocalDate() {
        User user = setUser();

        for(int i = 1; i< 20; i++){
            setDiary(user, i);
        }

        LocalDate startDate = LocalDate.of(2022, 5, 1);
        LocalDate endDate = LocalDate.of(2022, 5, 19);

        List<Emotion> emotionList = diaryJpaRepository.findEmotionListByLocalDate(user.getId(),startDate, endDate);
        System.out.println(emotionList);

    }
    private User setUser(){
        User user = User.builder()
                .username("test")
                .password("cucumber52")
                .email("test@gmail.com")
                .nickname("test")
                .imagePath("imagePath1").build();

        userJpaRepository.save(user);
        user = userJpaRepository.findByName("test");
        return user;
    }

    private Optional<Diary> setDiary(User user, int i){
        Diary diary = Diary.builder()
                .user(user)
                .writeDate(LocalDate.of(2022,5,i))
                .editionDate(LocalDateTime.now())
                .content("반가워요")
                .emotion((int)(Math.random() * 5)).thumbnailPath("thumbnailPath1").build();
        diaryJpaRepository.save(diary);
        Optional<Diary> findDiary = diaryJpaRepository.findByWriteDate(LocalDate.now());
        return findDiary;
    }
}