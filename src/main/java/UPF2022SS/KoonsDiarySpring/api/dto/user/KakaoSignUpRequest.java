package UPF2022SS.KoonsDiarySpring.api.dto.user;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class KakaoSignUpRequest {
    private String userId;

    private String nickname;

    private String imagePath;
}
