package UPF2022SS.KoonsDiarySpring.service.diary;

import UPF2022SS.KoonsDiarySpring.domain.Diary;
import UPF2022SS.KoonsDiarySpring.domain.DiaryImage;
import UPF2022SS.KoonsDiarySpring.domain.User;
import UPF2022SS.KoonsDiarySpring.repository.diary.DiaryImageJpaRepository;
import UPF2022SS.KoonsDiarySpring.repository.diary.DiaryJpaRepository;
import UPF2022SS.KoonsDiarySpring.repository.user.UserJpaRepository;
import UPF2022SS.KoonsDiarySpring.service.user.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ExtendWith(SpringExtension.class)
class DiaryImageServiceImplTest {
    @Autowired
    private DiaryImageService diaryImageService;
    @Autowired
    private DiaryService diaryService;
    @Autowired
    private DiaryJpaRepository diaryJpaRepository;
    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private UserService userService;

    @Test
    void saveImage_success(){

        //given
        User user = User.builder()
                .username("test")
                .email("test@gmail.com")
                .nickname("test")
                .password("testPwd").build();
        userService.join(user);

        Diary diary = Diary.builder()
                .user(user)
                .content("어제의 꿈은 오늘 잊혀지기 위해 존재한다.")
                .editionDate(LocalDateTime.now())
                .writeDate(LocalDate.now())
                .emotion(1)
                .thumbnailPath("11")
                .build();

        diaryJpaRepository.save(diary);

        String fileName = "testFileName";
        String comment = "testComment";

        //when
        DiaryImage diaryImage = diaryImageService.saveImage(fileName, comment, diary);

        //then
        assertThat(diaryImage).isNotNull();
        System.out.println("path = " + diaryImage.getImage_path());
        System.out.println("diary = " + diaryImage.getDiary());
        System.out.println("comment = " + diaryImage.getComment());
        System.out.println("id = " + diaryImage.getId());
    }


}