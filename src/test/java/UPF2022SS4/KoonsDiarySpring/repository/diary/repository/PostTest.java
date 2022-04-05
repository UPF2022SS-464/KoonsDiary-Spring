package UPF2022SS4.KoonsDiarySpring.repository.diary.repository;

import UPF2022SS4.KoonsDiarySpring.domain.Diary;
import UPF2022SS4.KoonsDiarySpring.domain.User;
import UPF2022SS4.KoonsDiarySpring.repository.diary.DiaryJpaRepository;
import UPF2022SS4.KoonsDiarySpring.repository.user.UserJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Transactional
@SpringBootTest
@ExtendWith(SpringExtension.class)
class PostTest {

    @Autowired
    private UserJpaRepository userJpaRepository;
    @Autowired
    private DiaryJpaRepository diaryJpaRepository;

    @Test
    void save_diary_success(){

        //given
        User user = User.builder()
                .username("orlando")
                .password("cucumber52")
                .nickname("ingkoon")
                .email("ing97220@naver.com")
                .build();
        userJpaRepository.save(user);

        final String content = "안녕하세요 저는 Koon 이라고 합니다";

        Diary diary = Diary.builder()
                .user(user)
                .writeDate(LocalDate.now())
                .editionDate(LocalDateTime.now())
                .content(content)
                .emotion(1)
                .diaryImageList(null)
                .thumbnailPath("koon이 잡아먹어 버린 썸네일 주소")
                .build();
        // when
        diaryJpaRepository.save(diary);
        // then
        System.out.println("diary info is = " + diary.getUser().getNickname() + diary.getContent() );
    }

    @Test
    void save_diary_fail() throws Exception{
        //given
        User user = User.builder()
                .username("orlando")
                .password("cucumber52")
                .nickname("ingkoon")
                .email("ing97220@naver.com")
                .build();
        userJpaRepository.save(user);

        final String content = "안녕하세요 저는 Koon 이라고 합니다";

        Diary diary = Diary.builder()
                .user(user)
                .writeDate(LocalDate.now())
                .editionDate(LocalDateTime.now())
                .content(content)
                .emotion(1)
//                .thumbnailPath("koon이 잡아먹어 버린 썸네일 주소")
                .build();
        // when
        try{
            diaryJpaRepository.save(diary);
        } catch (Exception e){
            //then
            System.out.println("Error is = " + e.getMessage());
        }
    }
}