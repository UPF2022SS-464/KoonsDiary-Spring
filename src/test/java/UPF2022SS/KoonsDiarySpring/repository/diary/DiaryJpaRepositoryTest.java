package UPF2022SS.KoonsDiarySpring.repository.diary;

import UPF2022SS.KoonsDiarySpring.QuerydslConfig;
import UPF2022SS.KoonsDiarySpring.domain.Diary;
import UPF2022SS.KoonsDiarySpring.domain.ImagePath;
import UPF2022SS.KoonsDiarySpring.domain.User;
import UPF2022SS.KoonsDiarySpring.repository.image.ImageJpaRepository;
import UPF2022SS.KoonsDiarySpring.repository.user.UserJpaRepository;
import org.assertj.core.api.Assertions;
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
class DiaryJpaRepositoryTest {

    @Autowired
    private DiaryJpaRepository diaryJpaRepository;
    @Autowired
    private UserJpaRepository userJpaRepository;
    @Autowired
    private ImageJpaRepository imageJpaRepository;

    @Test
    void save_diary_success(){
        ImagePath imagePath = setImage();
        User user = setUser(imagePath);

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

        ImagePath imagePath = setImage();
        User user = setUser(imagePath);

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

        Optional<User> test = userJpaRepository.findById(user.getId());
        List<Diary> diaries = diaryJpaRepository.findAllById(test.get().getId());

        for (Diary diary1 : diaries) {
            System.out.println("diary1 = " + diary1);
        }
    }

    @Test
    void findByAllUser_diary_success(){
        ImagePath imagePath = setImage();
        User user = setUser(imagePath);

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
        User test = userJpaRepository.findByUsername(user.getNickname()).get();
        List<Diary> diaries = diaryJpaRepository.findAllById(test.getId());

        for (Diary diary1 : diaries) {
            System.out.println("diary1 = " + diary1);
        }
    }

    public ImagePath setImage(){

        ImagePath imagePath = ImagePath.builder().path("profileN").build();
        imageJpaRepository.save(imagePath);

        return imagePath;
    }

    public User setUser(ImagePath imagePath){
        User user = User.builder()
                .username("koon")
                .password("cucumber52")
                .email("koon@gmail.com")
                .nickname("koon")
                .imagePath(imagePath)
                .build();

        userJpaRepository.save(user);

        return user;
    }
}