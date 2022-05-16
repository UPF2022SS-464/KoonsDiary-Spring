package UPF2022SS.KoonsDiarySpring.service.diary;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class GetDiaryImageTest {

    @Autowired
    private DiaryService diaryService;

    @Test
    void getDiaryImage() {
        ResponseEntity rs = diaryService.getDiaryImageV1("1KakaoTalk_Photo_2022-05-15-01-06-491652632952961.jpeg");
        Assertions.assertThat(rs).isNotNull();
        System.out.println("rs = " + rs);
    }
}