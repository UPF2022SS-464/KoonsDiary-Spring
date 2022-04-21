package UPF2022SS.KoonsDiarySpring.service.diary.sub;

import com.azure.ai.textanalytics.TextAnalyticsClient;
import com.azure.ai.textanalytics.TextAnalyticsClientBuilder;
import com.azure.ai.textanalytics.models.DocumentSentiment;
import com.azure.core.credential.AzureKeyCredential;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnalyticService {

    @Autowired
    private final AzureComponent azureComponent;

    public TextAnalyticsClient authenticateClient() {
        String key = azureComponent.getKey();
        String endpoint = azureComponent.getEndPoint();

        return new TextAnalyticsClientBuilder()
                .credential(new AzureKeyCredential(key))
                .endpoint(endpoint)
                .defaultLanguage("ko")
                .buildClient();
    }

    // Example method for sentiment in text
    public int AnalysisSentiment(TextAnalyticsClient client, String text) {

        DocumentSentiment documentSentiment = client.analyzeSentiment(text);

        Double positive = documentSentiment.getConfidenceScores().getPositive();
        Double neutral  = documentSentiment.getConfidenceScores().getNeutral();
        Double negative = documentSentiment.getConfidenceScores().getNegative();
        System.out.println("positive = " + positive + ", neutral = " + neutral + ", negative = " + negative);
        int result = 0;
        if((positive.compareTo(neutral) > 0) && positive.compareTo(negative) > 0){
            result = 1;
        }
        else if (neutral.compareTo(positive)>0 && neutral.compareTo(negative)>0){
            Double sumVal = positive + negative;
            if (neutral > sumVal){
                result = 3;
            }
            else if (sumVal > neutral && positive > negative){
                result = 2;
            }
            else result = 4;
        }
        else result=5;

        return result;
    }


}
