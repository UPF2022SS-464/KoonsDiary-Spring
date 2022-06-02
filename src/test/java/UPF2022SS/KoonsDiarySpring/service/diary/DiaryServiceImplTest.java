package UPF2022SS.KoonsDiarySpring.service.diary;

import UPF2022SS.KoonsDiarySpring.api.dto.diary.PostDiary;
import UPF2022SS.KoonsDiarySpring.domain.ImagePath;
import UPF2022SS.KoonsDiarySpring.domain.User;
import UPF2022SS.KoonsDiarySpring.service.image.ImageService;
import UPF2022SS.KoonsDiarySpring.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@SpringBootTest()
@ExtendWith(SpringExtension.class)
class DiaryServiceImplTest {

    @Autowired
    private UserService userService;

    @Autowired
    private DiaryService diaryService;

    @Autowired
    private ImageService imageService;

    @Test
    void Post_Diary_success(){

        ImagePath imagePath = ImagePath.builder().path("profile1").build();

        imageService.createImage(imagePath);

        User user = User.builder()
                .username("test")
                .password("cucumber52")
                .email("test@gmail.com")
                .nickname("test")
                .imagePath(imagePath)
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
        ResponseEntity<Object> response = diaryService.postDiary(request, findUser, files);
        System.out.println("defaultResponse = " + response.toString());
    }
}