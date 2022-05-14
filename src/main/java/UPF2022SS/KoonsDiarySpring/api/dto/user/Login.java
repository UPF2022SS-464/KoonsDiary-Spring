package UPF2022SS.KoonsDiarySpring.api.dto.user;


import lombok.AllArgsConstructor;
import lombok.Data;

public class Login {

    @Data
    public static class Request{
        private String userId;
        private String password;
    }
    @Data
    @AllArgsConstructor
    public static class Response{
        private Long userId;
        private String userName;
        private String accessToken;
        private String refreshToken;
    }
}


