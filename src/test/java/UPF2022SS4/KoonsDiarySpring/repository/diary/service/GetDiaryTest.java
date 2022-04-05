package UPF2022SS4.KoonsDiarySpring.repository.diary.service;

import UPF2022SS4.KoonsDiarySpring.api.dto.DefaultResponse;
import UPF2022SS4.KoonsDiarySpring.api.dto.diary.GetDiaryRequest;
import UPF2022SS4.KoonsDiarySpring.api.dto.diary.PostDiaryRequest;
import UPF2022SS4.KoonsDiarySpring.domain.DiaryImage;
import UPF2022SS4.KoonsDiarySpring.domain.User;
import UPF2022SS4.KoonsDiarySpring.repository.diary.DiaryJpaRepository;
import UPF2022SS4.KoonsDiarySpring.service.DiaryService;
import UPF2022SS4.KoonsDiarySpring.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Transactional
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class GetDiaryTest {

    @Autowired
    private UserService userService;
    @Autowired
    private DiaryJpaRepository diaryJpaRepository;
    @Autowired
    private DiaryService diaryService;

    @Test
    void get_diary_success() {
        //given
        User user = User.builder()
                .username("orlando")
                .password("cucumber52")
                .nickname("ingkoon")
                .email("ing97220@naver.com")
                .build();
        userService.join(user);

        final String content = "안녕하세요 저는 Koon 이라고 합니다";

        List<DiaryImage> diaryImageList = new ArrayList<>();

        PostDiaryRequest postDiaryRequest = PostDiaryRequest.builder()
                .writeDate(LocalDate.now())
                .editionDate(LocalDateTime.now())
                .content(content)
                .emotion(1)
                .diaryImageList(diaryImageList)
                .thumbnailPath("koon이 잡아먹어 버린 썸네일 주소")
                .build();

        // when
        DefaultResponse postResponse = (DefaultResponse) diaryService.postDiary(postDiaryRequest).getData();
        DefaultResponse response = diaryService.getDiary(new GetDiaryRequest(1L));

        // then
        System.out.println("동작 결과 = " + response);
    }

    @Test
    void get_diary_fail(){

    }

}
