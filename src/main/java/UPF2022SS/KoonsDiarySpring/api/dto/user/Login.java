package UPF2022SS.KoonsDiarySpring.api.dto.user;


import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

public class Login {

    @Data
    public static class Request{
        @NotBlank(message = "아이디를 올바르게 입력해 주세요")
        private String userId;
        @NotBlank(message = "비밀번호를 올바르게 입력해 주세요")
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


