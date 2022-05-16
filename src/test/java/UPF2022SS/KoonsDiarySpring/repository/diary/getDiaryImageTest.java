package UPF2022SS.KoonsDiarySpring.repository.diary;

import UPF2022SS.KoonsDiarySpring.QuerydslConfig;
import UPF2022SS.KoonsDiarySpring.domain.Diary;
import UPF2022SS.KoonsDiarySpring.domain.DiaryImage;
import UPF2022SS.KoonsDiarySpring.domain.User;
import UPF2022SS.KoonsDiarySpring.repository.user.UserJpaRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(QuerydslConfig.class)
@ExtendWith(SpringExtension.class)
class getDiaryImageTest {

    @Autowired
    private UserJpaRepository userJpaRepository;
    @Autowired
    private DiaryJpaRepository diaryJpaRepository;
    @Autowired
    private DiaryImageJpaRepository diaryImageJpaRepository;

    @Test
    void findByDiaryId() {
        //given
        User user = setUser();
        Optional<Diary> diary = setDiary(user);

        //when
        DiaryImage diaryImage = DiaryImage.builder()
                .diary(diary.get())
                .image_path("imagePath1").comment("반가워요").build();
        diaryImageJpaRepository.save(diaryImage);

        List<DiaryImage> diaryImageList = diaryImageJpaRepository.findByDiaryId(diary.get().getId());

        //then
        for (DiaryImage image : diaryImageList) {
            assertThat(diaryImage).isEqualTo(image);
            System.out.println("image = " + image.getDiary() + " " + image.getImage_path() + " " + image.getComment());
        }
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

    private Optional<Diary> setDiary(User user){
        Diary diary = Diary.builder()
                .user(user)
                .writeDate(LocalDate.now())
                .editionDate(LocalDateTime.now())
                .content("반가워요")
                .emotion(1).thumbnailPath("thumbnailPath1").build();
        diaryJpaRepository.save(diary);
        Optional<Diary> findDiary = diaryJpaRepository.findByWriteDate(LocalDate.now());
        return findDiary;
    }
}