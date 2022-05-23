package UPF2022SS.KoonsDiarySpring.api.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

public class ValidationEmail {

    @Data
    public static class Request{
        @NotNull(message = "이메일을 입력해 주세요")
        private String email;
    }

    @Data
    @AllArgsConstructor
    public static class  Response{
        private Boolean result;
    }
}
