package UPF2022SS.KoonsDiarySpring.service.diary;


import UPF2022SS.KoonsDiarySpring.api.dto.DefaultResponse;
import UPF2022SS.KoonsDiarySpring.common.ResponseMessage;
import UPF2022SS.KoonsDiarySpring.common.StatusCode;
import UPF2022SS.KoonsDiarySpring.domain.Diary;
import UPF2022SS.KoonsDiarySpring.domain.User;
import UPF2022SS.KoonsDiarySpring.repository.diary.DiaryJpaRepository;
import UPF2022SS.KoonsDiarySpring.service.user.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class DeleteDiaryTest {

    @Autowired
    private UserService userService;

    @Autowired
    private DiaryJpaRepository diaryJpaRepository;

    @Autowired
    private DiaryService diaryService;

    @Autowired
    private EntityManager em;

    @Test
    void delete_success_diary() throws Exception{
        User user = User.builder()
                .username("test")
                .password("cucumber52")
                .email("test@gmail.com")
                .nickname("test")
                .imagePath("imagePath1")
                .build();

        userService.join(user);

        User findUser = userService.findUserEmail(user.getUsername());

        Diary diary = Diary.builder()
                .user(user)
                .writeDate(LocalDate.now())
                .editionDate(LocalDateTime.now())
                .content("테스트 내용입니다.")
                .thumbnailPath("11")
                .emotion(4)
                .build();

        diaryJpaRepository.save(diary);

        DefaultResponse response = diaryService.deleteDiary(diary.getId());

        assertThat(response.getStatus()).isEqualTo(StatusCode.OK);
        System.out.println("response = " + response);
    }

    @Test
    void delete_fail_diary() throws Exception{
        //given
        User user = User.builder()
                .username("test")
                .password("cucumber52")
                .email("test@gmail.com")
                .nickname("test")
                .imagePath("imagePath1")
                .build();

        userService.join(user);

        User findUser = userService.findUsername("test");

        Diary diary = Diary.builder()
                .user(findUser)
                .writeDate(LocalDate.now())
                .editionDate(LocalDateTime.now())
                .content("테스트 내용입니다.")
                .thumbnailPath("11")
                .emotion(4)
                .build();

        diaryJpaRepository.save(diary);

        // when
        DefaultResponse response = diaryService.deleteDiary(290L);

        // then
        assertThat(response.getStatus()).isEqualTo(StatusCode.BAD_REQUEST);
        System.out.println("response = " + response);
    }
}
