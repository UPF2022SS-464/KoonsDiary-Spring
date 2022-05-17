package UPF2022SS.KoonsDiarySpring.api.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

public class SignUp {

    @Data
    public static class Request {
        @NotBlank(message = "아이디를 올바르게 입력해 주세요")
        private String userId;
        @NotBlank(message = "비밀번호를 제대로 입력해 주세요")
        private String password;
        @NotBlank(message = "이메일을 제대로 입력해 주세요")
        private String email;
        @NotBlank(message = "닉네임을 제대로 입력해 주세요")
        private String nickname;
        private String imagePath;

    }

    @Data
    @AllArgsConstructor
    public static class Response {

        private String accessToken;

        private String refreshToken;

        private String userId;
    }



}
