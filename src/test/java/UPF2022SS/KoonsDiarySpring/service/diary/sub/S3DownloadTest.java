package UPF2022SS.KoonsDiarySpring.service.diary.sub;

import UPF2022SS.KoonsDiarySpring.QuerydslConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;


@Import(QuerydslConfig.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
class S3DownloadTest {

    @Autowired
    private S3Service s3Service;

    @Test
    void downloadFile() throws FileNotFoundException {
        System.out.println("start");
        byte[] file = s3Service.downloadFile("1KakaoTalk_Photo_2022-05-15-01-06-491652632952961.jpeg");
        System.out.println("end");
        Assertions.assertThat(file).isNotEmpty();
//        System.out.println("file = " + file);
    }
}