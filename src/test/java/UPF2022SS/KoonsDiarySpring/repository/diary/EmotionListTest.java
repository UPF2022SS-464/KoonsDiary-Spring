package UPF2022SS.KoonsDiarySpring.repository.diary;

import UPF2022SS.KoonsDiarySpring.QuerydslConfig;
import UPF2022SS.KoonsDiarySpring.api.dto.diary.Emotion;
import UPF2022SS.KoonsDiarySpring.domain.Diary;
import UPF2022SS.KoonsDiarySpring.domain.ImagePath;
import UPF2022SS.KoonsDiarySpring.domain.User;
import UPF2022SS.KoonsDiarySpring.repository.image.ImageJpaRepository;
import UPF2022SS.KoonsDiarySpring.repository.user.UserJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@Import(QuerydslConfig.class)
class EmotionListTest {

    @Autowired
    private DiaryJpaRepository diaryJpaRepository;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private ImageJpaRepository imageJpaRepository;

    @Test
    void findEmotionListByLocalDate() {
        ImagePath imagePath = setImage();
        User user = setUser(imagePath);

        for(int i = 1; i< 20; i++){
            setDiary(user, i);
        }

        LocalDate startDate = LocalDate.of(2022, 5, 1);
        LocalDate endDate = LocalDate.of(2022, 5, 19);

        List<Emotion> emotionList = diaryJpaRepository.findEmotionListByLocalDate(user.getId(),startDate, endDate);
        System.out.println(emotionList);

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

    public ImagePath setImage(){

        String path = "profile1";
        ImagePath imagePath = ImagePath.builder()
                .path(path)
                .build();

        imageJpaRepository.save(imagePath);

        ImagePath findImagePath = imageJpaRepository.findByPath(path).get();

        return findImagePath;
    }

    public User setUser(ImagePath imagePath){
        User user = User.builder()
                .username("koon")
                .userPwd("cucumber52")
                .email("koon@gmail.com")
                .nickname("koon")
                .imagePath(imagePath)
                .build();

        userJpaRepository.save(user);

        User findUser = userJpaRepository.findByName("koon");
        return findUser;
    }
}