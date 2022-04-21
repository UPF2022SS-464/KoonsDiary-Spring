package UPF2022SS.KoonsDiarySpring.service.diary;

import UPF2022SS.KoonsDiarySpring.service.diary.sub.AnalyticService;
import com.azure.ai.textanalytics.TextAnalyticsClient;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@SpringBootTest
@ExtendWith(SpringExtension.class)
class AnalyticServiceTest {

    @Autowired
    AnalyticService analyticService;

    @Test
    void validation_text_success(){
        TextAnalyticsClient textAnalyticsClient = analyticService.authenticateClient();
        Assertions.assertThat(textAnalyticsClient).isNotNull();
        System.out.println("textAnalyticsClient = " + textAnalyticsClient);
    }

    @Test
    void Analytic_Text_success(){
        TextAnalyticsClient client = analyticService.authenticateClient();

        String text= "마음속에 얽힌 응어리가 이리저리 움직이며 뾰족한 가시로 내 마음을 찔러댄 하루. " +
                "좋은 사람들이었다고 생각되던 사람들은 나를 가벼운 관계로밖에 보지 않는 것 같았다." +
                "내가 부족해서 그런 것 같다.";
        int result = analyticService.AnalysisSentiment(client, text);
        System.out.println("result = " + result);
    }
}