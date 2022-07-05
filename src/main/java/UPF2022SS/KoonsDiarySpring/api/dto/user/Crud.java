package UPF2022SS.KoonsDiarySpring.api.dto.user;

import UPF2022SS.KoonsDiarySpring.domain.ImagePath;
import UPF2022SS.KoonsDiarySpring.domain.RefreshToken;
import UPF2022SS.KoonsDiarySpring.domain.User;
import lombok.*;

public class Crud {

    public static class Create{
        @AllArgsConstructor
        @NoArgsConstructor
        @ToString
        @Builder
        @Getter
        public static class RequestDto{
            private String userId;
            private String password;
            private String email;
            private String nickname;
            private Long imageId;

            public User toEntity(String password, ImagePath imagePath){
                User user = User.builder().username(userId)
                        .password(password)
                        .email(email)
                        .nickname(nickname)
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
            private Long id;
            private String userId;
            private String refreshToken;
            private String accessToken;

            public static ResponseDto of(User user, String accessToken){
                return ResponseDto.builder()
                        .id(user.getId())
                        .userId(user.getUsername())
                        .refreshToken(user.getRefreshToken().getValue())
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
        public static class RequestDto{
            private String id;
            private String password;
        }


        @AllArgsConstructor
        @NoArgsConstructor
        @ToString
        @Builder
        @Getter
        public static class ResponseDto{
            private String userId;
            private String accessToken;
            private String refreshToken;

            public static ResponseDto of(User user, String accessToken){
                return ResponseDto.builder()
                        .userId(user.getUsername())
                        .accessToken(accessToken)
                        .refreshToken(user.getRefreshToken().getValue()).build();
            }
        }
    }
    public static class Update{

    }
    public  static class Delete{

    }
}
