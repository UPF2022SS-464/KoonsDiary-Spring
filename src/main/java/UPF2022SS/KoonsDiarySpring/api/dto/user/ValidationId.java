package UPF2022SS.KoonsDiarySpring.api.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


public class ValidationId {
    @Data
    public static class Request{
        @NotBlank(message = "아이디를 입력해 주세요")
        private String id;
    }

    @Data
    @AllArgsConstructor
    public static class Response{
        private Boolean result;
    }
}
