package UPF2022SS.KoonsDiarySpring.api.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class Kakao {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AccessDto{
        private Long id;
    }

    @Data
    public static class SignUpRequset{

            @NotBlank(message = "카카오 아이디를 올바르게 입력해 주세요")
            private Long kakaoId;
            @NotBlank(message = "아이디를 올바르게 입력해 주세요")
            private String userId;
            @NotBlank(message = "비밀번호를 제대로 입력해 주세요")
            private String password;
            @NotBlank(message = "닉네임을 제대로 입력해 주세요")
            private String nickname;
            @NotNull(message = "이미지를 제대로 설정해 주세요.")
            private Long imageId;
        }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SignUpResponse{
        private String accessToken;
    }

    @Data
    public static class SignInRequset{
        @NotBlank(message = "카카오 아이디를 올바르게 입력해 주세요")
        private Long kakaoId;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SignInResponse{
        private String accessToken;
    }
}
