package UPF2022SS.KoonsDiarySpring.service.diary.sub;

import UPF2022SS.KoonsDiarySpring.QuerydslConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(QuerydslConfig.class)
@ExtendWith(SpringExtension.class)
class S3DownloadTest {

    @Autowired
    private S3Service s3Service;

    @Test
    void downloadFile() {
        byte[] file = s3Service.downloadFile("s3://koonsdiaryimage/1즐겁니?1652632953906.jpeg");
        System.out.println("file = " + file);
    }
}