package UPF2022SS.KoonsDiarySpring.service.diary;

import UPF2022SS.KoonsDiarySpring.api.dto.DefaultResponse;
import UPF2022SS.KoonsDiarySpring.domain.Diary;
import UPF2022SS.KoonsDiarySpring.domain.ImagePath;
import UPF2022SS.KoonsDiarySpring.domain.User;
import UPF2022SS.KoonsDiarySpring.repository.diary.DiaryJpaRepository;
import UPF2022SS.KoonsDiarySpring.repository.user.UserJpaRepository;
import UPF2022SS.KoonsDiarySpring.service.image.ImageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@SpringBootTest
@Transactional
@ExtendWith(SpringExtension.class)
class EmotionStatisticTest {

    @Autowired
    private UserJpaRepository userJpaRepository;
    @Autowired
    private DiaryJpaRepository diaryJpaRepository;
    @Autowired
    private ImageService imageService;
    @Autowired
    private DiaryService diaryService;

    @Test
    void getEmotionListByLocalDate() {
        //given
        ImagePath imagePath = ImagePath.builder()
                .path("profile1")
                .build();

        imageService.createImage(imagePath);

        User user = User.builder()
                .username("test")
                .userPwd("cucumber52")
                .email("test@gmail.com")
                .nickname("test")
                .imagePath(imagePath)
                .build();

        userJpaRepository.save(user);

        for(int i = 1; i< 20; i++){
            setDiary(user, i);
        }

        LocalDate startDate = LocalDate.of(2022,5,1);
        LocalDate endDate = LocalDate.of(2022,5,30);

        ResponseEntity<Object> response = diaryService.getEmotionListByLocalDate(user, startDate, endDate);


        System.out.println("response = " + response);
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


    // 19개의 다이어리 삽입 수행
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