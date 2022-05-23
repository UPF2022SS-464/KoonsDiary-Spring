package UPF2022SS.KoonsDiarySpring.api.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;


public class ValidationId {

    @Data
    public static class Request{
        private String id;
    }

    @Data
    @AllArgsConstructor
    public static class Response{
        private Boolean result;
    }
}
