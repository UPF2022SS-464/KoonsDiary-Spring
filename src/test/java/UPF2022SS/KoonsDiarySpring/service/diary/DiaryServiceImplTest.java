package UPF2022SS.KoonsDiarySpring.service.diary;

import UPF2022SS.KoonsDiarySpring.api.dto.DefaultResponse;
import UPF2022SS.KoonsDiarySpring.api.dto.diary.PostDiary;
import UPF2022SS.KoonsDiarySpring.api.dto.user.SignUpRequest;
import UPF2022SS.KoonsDiarySpring.domain.Diary;
import UPF2022SS.KoonsDiarySpring.domain.User;
import UPF2022SS.KoonsDiarySpring.service.user.UserService;
import org.assertj.core.api.Assertions;
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

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest()
@ExtendWith(SpringExtension.class)
class DiaryServiceImplTest {

    @Autowired
    private UserService userService;

    @Autowired
    private DiaryService diaryService;

    @Test
    void Post_Diary_success(){
        User user = User.builder()
                .username("test")
                .password("cucumber52")
                .email("test@gmail.com")
                .nickname("test")
                .imagePath("imagePath1")
                .build();

        userService.join(user);

        User findUser = userService.findUsername(user.getUsername());
        List<String> comment = new ArrayList<>();
        List<String> files = new ArrayList<String>();

        for(int i = 0; i <3; i++){
            files.add("test"+Integer.toString(i));
            comment.add("test"+Integer.toString(i));
        }
        PostDiary.Request request = new PostDiary.Request("어제의 꿈은 오늘 잊혀지기 위해 존재한다.", comment);


        //이부분을 헤더를 빼고유저 객체가 들어갈 수 있게하자.
        DefaultResponse defaultResponse = diaryService.postDiary(request, findUser, files);
        System.out.println("defaultResponse = " + defaultResponse.toString());
    }
}