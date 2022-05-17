package UPF2022SS.KoonsDiarySpring.repository.diary;

import UPF2022SS.KoonsDiarySpring.QuerydslConfig;
import UPF2022SS.KoonsDiarySpring.api.dto.diary.MonthlyDiary;
import UPF2022SS.KoonsDiarySpring.domain.Diary;
import UPF2022SS.KoonsDiarySpring.domain.User;
import UPF2022SS.KoonsDiarySpring.repository.user.UserJpaRepository;
import com.querydsl.core.Tuple;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(QuerydslConfig.class)
@ExtendWith(SpringExtension.class)
class MonthTest {
    @Autowired
    private UserJpaRepository userJpaRepository;
    @Autowired
    private DiaryJpaRepository diaryJpaRepository;

    @Test
    void findListByMonth_Success() {

        //given
        User user = setUser();
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
        User user = setUser();
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