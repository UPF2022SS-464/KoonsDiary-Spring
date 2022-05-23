package UPF2022SS.KoonsDiarySpring.api.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

public class Kakao {

    @Data
    public static class SignUpRequset{
        @NotBlank
        private String kakaoToken;

        @NotBlank
        private String userId;

        @NotBlank
        private String nickname;

        @NotBlank
        private Long imageId;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SignUpResponse{
        private String accessToken;
    }
}
