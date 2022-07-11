package UPF2022SS.KoonsDiarySpring.api.dto.user;

import UPF2022SS.KoonsDiarySpring.domain.ImagePath;
import UPF2022SS.KoonsDiarySpring.domain.User;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class KakaoDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AccessDto{
        private Long id;
    }

    public static class Create{
        @AllArgsConstructor
        @NoArgsConstructor
        @ToString
        @Builder
        @Getter
        public static class RequestDto{
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

            public User toEntity(String password, ImagePath imagePath){
                User user = User.builder()
                        .username(userId)
                        .password(password)
                        .nickname(nickname)
                        .kakaoId(kakaoId)
                        .imagePath(imagePath)
                        .build();
                return user;
            }
        }
        @AllArgsConstructor
        @NoArgsConstructor
        @ToString
        @Builder
        @Getter
        public static class ResponseDto{
            private String nickname;
            private String imagePath;
            private String accessToken;

            public static ResponseDto of(User user, String accessToken){
                return ResponseDto
                        .builder()
                        .nickname(user.getNickname())
                        .imagePath(user.getImagePath().getPath())
                        .accessToken(accessToken)
                        .build();
            }
        }

    }


    public static class Read{
        @AllArgsConstructor
        @NoArgsConstructor
        @ToString
        @Builder
        @Getter
        public static class ResponseDto {
            private Long kakaoId;

            public static ResponseDto of(Long kakaoId){
                return ResponseDto.builder().kakaoId(kakaoId).build();
            }
        }
    }
}