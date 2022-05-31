package UPF2022SS.KoonsDiarySpring.service.diary;

import UPF2022SS.KoonsDiarySpring.domain.Diary;
import UPF2022SS.KoonsDiarySpring.domain.ImagePath;
import UPF2022SS.KoonsDiarySpring.domain.User;
import UPF2022SS.KoonsDiarySpring.repository.diary.DiaryJpaRepository;
import UPF2022SS.KoonsDiarySpring.repository.user.UserJpaRepository;
import UPF2022SS.KoonsDiarySpring.service.image.ImageService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@SpringBootTest
@Transactional
@ExtendWith(MockitoExtension.class)
class CalenderTest {

    @Mock
    private UserJpaRepository userJpaRepository;
    @Mock
    private DiaryJpaRepository diaryJpaRepository;
    @Autowired
    private ImageService imageService;
    @InjectMocks
    private DiaryServiceImpl diaryService;


    @Test
    @DisplayName("월에 대한 다이어리 DTO 출력")
    void getMonthlyDiaryListByLocalDate() {
        //given
        User user = setUser();
        for(int i = 1; i< 20; i++){
            setDiary(user, i);
        }

        LocalDate startDate = LocalDate.of(2022,5,1);
        LocalDate endDate = LocalDate.of(2022,5,30);

        ResponseEntity<Object> response = diaryService.getMonthlyDiaryListByLocalDate(user, startDate, endDate);

//        Assertions.assertThat(response.getData()).isNotNull();

        System.out.println("response = " + response);
    }

    //유저 정보 설정
    private User setUser(){
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
        user = userJpaRepository.findByName("test");
        return user;
    }

    // 19개의 다이어리 삽입 수행
    private Optional<Diary> setDiary(User user, int i){
        Diary diary = Diary.builder()
                .user(user)
                .writeDate(LocalDate.of(2022, 5, i))
                .editionDate(LocalDateTime.now())
                .content("반가워요")
                .emotion(1).thumbnailPath("thumbnailPath1").build();
        diaryJpaRepository.save(diary);
        Optional<Diary> findDiary = diaryJpaRepository.findByWriteDate(LocalDate.now());
        return findDiary;
    }

}