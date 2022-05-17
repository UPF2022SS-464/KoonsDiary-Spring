package UPF2022SS.KoonsDiarySpring.service.diary;

import UPF2022SS.KoonsDiarySpring.api.dto.DefaultResponse;
import UPF2022SS.KoonsDiarySpring.api.dto.diary.MonthlyDiary;
import UPF2022SS.KoonsDiarySpring.domain.Diary;
import UPF2022SS.KoonsDiarySpring.domain.User;
import UPF2022SS.KoonsDiarySpring.repository.diary.DiaryJpaRepository;
import UPF2022SS.KoonsDiarySpring.repository.user.UserJpaRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CalenderTest {

    @Mock
    private UserJpaRepository userJpaRepository;
    @Mock
    private DiaryJpaRepository diaryJpaRepository;
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

        DefaultResponse response = diaryService.getMonthlyDiaryListByLocalDate(user, startDate, endDate);

//        Assertions.assertThat(response.getData()).isNotNull();

        System.out.println("response = " + response);
    }

    //유저 정보 설정
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