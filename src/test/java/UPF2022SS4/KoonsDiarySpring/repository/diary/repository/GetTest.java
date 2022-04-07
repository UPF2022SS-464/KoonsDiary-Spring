package UPF2022SS4.KoonsDiarySpring.repository.diary.repository;

import UPF2022SS4.KoonsDiarySpring.common.ResponseMessage;
import UPF2022SS4.KoonsDiarySpring.common.StatusCode;
import UPF2022SS4.KoonsDiarySpring.domain.Diary;
import UPF2022SS4.KoonsDiarySpring.domain.User;
import UPF2022SS4.KoonsDiarySpring.repository.diary.DiaryJpaRepository;
import UPF2022SS4.KoonsDiarySpring.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Transactional
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class GetTest {
    @Autowired
    private UserService userService;
    @Autowired
    private DiaryJpaRepository diaryJpaRepository;

    @Test
    void get_diary_success(){
        //given
        User user = User.builder()
                .username("orlando")
                .password("cucumber52")
                .nickname("ingkoon")
                .email("ing97220@naver.com")
                .build();
        userService.join(user);

        final String content = "안녕하세요 저는 Koon 이라고 합니다";

        Diary diary = Diary.builder()
                .user(user)
                .writeDate(LocalDate.now())
                .editionDate(LocalDateTime.now())
                .content(content)
                .emotion(1)
                .thumbnailPath("koon이 잡아먹어 버린 썸네일 주소")
                .build();
        // when
        diaryJpaRepository.save(diary);
        Optional<Diary> diary1 = diaryJpaRepository.findById(diary.getId());

        // then
        System.out.println(diary1.get().getUser().getNickname() + " " + diary1.get().getContent());

    }

    @Test
    void get_diary_fail() throws Exception{
        //given
        User user = User.builder()
                .username("orlando")
                .password("cucumber52")
                .nickname("ingkoon")
                .email("ing97220@naver.com")
                .build();
        userService.join(user);

        final String content = "안녕하세요 저는 Koon 이라고 합니다";

        Diary diary = Diary.builder()
                .user(user)
                .writeDate(LocalDate.now())
                .editionDate(LocalDateTime.now())
                .content(content)
                .emotion(1)
                .thumbnailPath("koon이 잡아먹어 버린 썸네일 주소")
                .build();

        Long id = 1232132133L;
        // when
        diaryJpaRepository.save(diary);

        try{
            diaryJpaRepository.save(diary);
            Optional<Diary> diary1 = diaryJpaRepository.findById(diary.getId());
        }
        catch (Exception e){
            // then
            System.out.println(StatusCode.DB_ERROR + ResponseMessage.DIARY_GET_FAIL);
            System.out.println("e.getMessage() = " + e.getMessage());
        }
    }
}
