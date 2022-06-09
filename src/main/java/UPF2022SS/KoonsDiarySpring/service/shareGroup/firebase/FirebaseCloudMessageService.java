package UPF2022SS.KoonsDiarySpring.service.shareGroup.firebase;

import UPF2022SS.KoonsDiarySpring.api.dto.shareGroup.FcmMessage;
import UPF2022SS.KoonsDiarySpring.api.dto.shareGroup.FcmMessageV2;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.gson.JsonParseException;
import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FirebaseCloudMessageService {
    private final String API_URL = "https://fcm.googleapis.com/v1/projects/android-****/messages:send";
    private final ObjectMapper objectMapper;

    private String getAccessToken()throws IOException{
        String firebaseConfigPath = "firebase/firebase_service_key.json";

        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource((firebaseConfigPath)).getInputStream())
                .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));

        googleCredentials.refreshIfExpired();
        return googleCredentials.getAccessToken().getTokenValue();
    }

    public void sendMessageTo(String targetToken, String title, String body) throws IOException{
        String message = makeMessage(targetToken, title, body);

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; cahrset=utf-8"), message);

        Request request = new Request.Builder()
                .url(API_URL)
                .post(requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                .build();

        Response response = client.newCall(request).execute();

        System.out.println(response.body().string());

    }

//    public void sendMessage(List<String> fcmTokens, String title, String body) throws FirebaseMessagingException {
//        MulticastMessage message = MulticastMessage.builder()
//                .putData("text", "그룹일기에 초대되었습니다.")
//                .addAllTokens(fcmTokens)
//                .build();
//        FirebaseMessaging.getInstance().sendMulticast(message);
//    }

    private String makeMessages(List<String> targetToken, String title, String body) throws JsonProcessingException {

        FcmMessageV2 fcmMessage = FcmMessageV2.builder()
                .message(FcmMessageV2.Message.builder()
                        .token(targetToken)
                        .notification(FcmMessageV2.Notification.builder()
                                .title(title)
                                .body(body)
                                .image(null)
                                .build()
                        ).build()).validate_only(false).build();
//        MulticastMessage message = MulticastMessage.builder()
//                .addAllTokens(targetToken)
//                .setNotification(Notification.builder()
//                        .setTitle(title)
//                        .setBody(body)
//                        .setImage(null).build()
//                ).build();

        return objectMapper.writeValueAsString(fcmMessage);
    }
    private String makeMessage(String targetToken, String title, String body) throws JsonProcessingException {
        FcmMessage message = FcmMessage.builder()
                .message(FcmMessage.Message.builder()
                        .token(targetToken)
                        .notification(FcmMessage.Notification.builder()
                                .title(title)
                                .body(body)
                                .image(null).build())
                        .build()).validate_only(false).build();

        return objectMapper.writeValueAsString(message);
    }
}
