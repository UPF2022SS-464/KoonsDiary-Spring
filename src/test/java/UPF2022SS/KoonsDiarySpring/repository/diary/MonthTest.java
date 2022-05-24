package UPF2022SS.KoonsDiarySpring.repository.diary;

import UPF2022SS.KoonsDiarySpring.QuerydslConfig;
import UPF2022SS.KoonsDiarySpring.api.dto.diary.MonthlyDiary;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@DataJpaTest
@Import(QuerydslConfig.class)
class MonthTest {
    @Autowired
    private UserJpaRepository userJpaRepository;
    @Autowired
    private DiaryJpaRepository diaryJpaRepository;
    @Autowired
    private ImageJpaRepository imageJpaRepository;

    @Test
    void findListByMonth_Success() {
        //given
        ImagePath imagePath = setImage();
        User user = setUser(imagePath);
        for(int i = 1; i< 20; i++){
            setDiary(user, i);
        }

        LocalDate startData = LocalDate.of(2022,5,1);
        LocalDate endData = LocalDate.of(2022,5,30);

        //when
        List<MonthlyDiary> diaryList = diaryJpaRepository.findListByMonth(user.getId(), startData, endData);
        Map<LocalDate, List<String>> n = new HashMap<>();
        for (MonthlyDiary diary : diaryList) {
            n.put(diary.getWriteDate(), List.of(new String[]{diary.getId().toString(), Integer.toString(diary.getEmotion())}));
        }
        System.out.println("n = " + n);
        //then
        Assertions.assertThat(diaryList).isNotNull();
        System.out.println("diaryList = " + diaryList);
    }

    @Test
    void findListByMonth_fail() {
        //given
        ImagePath imagePath = setImage();
        User user = setUser(imagePath);

        for(int i = 1; i< 20; i++){
            setDiary(user, i);
        }

        LocalDate startData = LocalDate.of(2022,6,1);
        LocalDate endData = LocalDate.of(2022,6,30);

        //when
        List<MonthlyDiary> diaryList = diaryJpaRepository.findListByMonth(user.getId(), startData, endData);

        //then
        Assertions.assertThat(diaryList).isEmpty();
        System.out.println("diaryList = " + diaryList);
    }

    public ImagePath setImage(){

        ImagePath imagePath = ImagePath.builder().path("profileN").build();
        imageJpaRepository.save(imagePath);
        return imagePath;
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
