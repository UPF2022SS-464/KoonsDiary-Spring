package UPF2022SS.KoonsDiarySpring.repository.diary;

import UPF2022SS.KoonsDiarySpring.QuerydslConfig;
import UPF2022SS.KoonsDiarySpring.domain.Diary;
import UPF2022SS.KoonsDiarySpring.domain.DiaryImage;
import UPF2022SS.KoonsDiarySpring.domain.ImagePath;
import UPF2022SS.KoonsDiarySpring.domain.User;
import UPF2022SS.KoonsDiarySpring.repository.image.ImageJpaRepository;
import UPF2022SS.KoonsDiarySpring.repository.user.UserJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@Import(QuerydslConfig.class)
@ExtendWith(SpringExtension.class)
class getDiaryImageTestPath {

    @Autowired
    private UserJpaRepository userJpaRepository;
    @Autowired
    private DiaryJpaRepository diaryJpaRepository;
    @Autowired
    private DiaryImageJpaRepository diaryImageJpaRepository;
    @Autowired
    private ImageJpaRepository imageJpaRepository;

    @Test
    void findByDiaryId() {
        ImagePath imagePath = setImage();
        //given
        User user = setUser(imagePath);
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