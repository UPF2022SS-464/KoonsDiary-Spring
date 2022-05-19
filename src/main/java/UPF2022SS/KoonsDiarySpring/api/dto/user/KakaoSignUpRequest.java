package UPF2022SS.KoonsDiarySpring.api.dto.user;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class KakaoSignUpRequest {

    @NotBlank
    private String userId;

    @NotBlank
    private String nickname;

    @NotBlank
    private Long imageId;
}
