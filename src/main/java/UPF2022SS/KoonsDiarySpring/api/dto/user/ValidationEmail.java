package UPF2022SS.KoonsDiarySpring.api.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

public class ValidationEmail {

    @Data
    public static class Request{
        private String email;
    }

    @Data
    @AllArgsConstructor
    public static class  Response{
        private Boolean result;
    }
}
