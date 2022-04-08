package UPF2022SS.KoonsDiarySpring.repository.diary;

import UPF2022SS.KoonsDiarySpring.domain.Diary;
import UPF2022SS.KoonsDiarySpring.domain.User;
import UPF2022SS.KoonsDiarySpring.repository.user.UserJpaRepository;
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
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
class DiaryJpaRepositoryTest {

    @Autowired
    private DiaryJpaRepository diaryJpaRepository;
    @Autowired
    private UserJpaRepository userJpaRepository;

    @Test
    void save_diary_success(){
        User user = User.builder()
                .username("koon")
                .password("cucumber52")
                .email("koon@gmail.com")
                .nickname("koon")
                .build();

        userJpaRepository.save(user);

        String content = "Test Content";

        Diary diary = Diary.builder()
                .user(user)
                .writeDate(LocalDate.now())
                .editionDate(LocalDateTime.now())
                .content(content)
                .emotion(1)
                .thumbnailPath("koon이 잡아먹어 버린 썸네일 주소")
                .build();

        diaryJpaRepository.save(diary);
        diary.setUser(user);

        Assertions.assertThat(user.getId()).isEqualTo(diary.getUser().getId());
    }

    @Test
    void findById_diary_success(){
        User user = User.builder()
                .username("koon")
                .password("cucumber52")
                .email("koon@gmail.com")
                .nickname("koon")
                .build();

        userJpaRepository.save(user);

        String content = "Test Content";

        Diary diary = Diary.builder()
                .user(user)
                .writeDate(LocalDate.now())
                .editionDate(LocalDateTime.now())
                .content(content)
                .emotion(1)
                .thumbnailPath("koon이 잡아먹어 버린 썸네일 주소")
                .build();

        diaryJpaRepository.save(diary);
        diary.setUser(user);
        Optional<User> test = userJpaRepository.findById(user.getId());
        List<Diary> diaries = diaryJpaRepository.findAllById(test.get().getId());

        for (Diary diary1 : diaries) {
            System.out.println("diary1 = " + diary1);
        }
    }

    @Test
    void findByAllUser_diary_success(){
        User user = User.builder()
                .username("koon")
                .password("cucumber52")
                .email("koon@gmail.com")
                .nickname("koon")
                .build();

        userJpaRepository.save(user);

        String content = "Test Content";

        Diary diary = Diary.builder()
                .user(user)
                .writeDate(LocalDate.now())
                .editionDate(LocalDateTime.now())
                .content(content)
                .emotion(1)
                .thumbnailPath("koon이 잡아먹어 버린 썸네일 주소")
                .build();

        diaryJpaRepository.save(diary);
        diary.setUser(user);
        User test = userJpaRepository.findByName(user.getNickname());
        List<Diary> diaries = diaryJpaRepository.findAllById(test.getId());

        for (Diary diary1 : diaries) {
            System.out.println("diary1 = " + diary1);
        }
    }
}