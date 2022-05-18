package UPF2022SS.KoonsDiarySpring.api.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class UpdateUser {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        private String nickname;
        private String password;
        private String imagePath;
    }

    @Data
    @AllArgsConstructor
    public  static class Response{
        private String comment;
    }
}
