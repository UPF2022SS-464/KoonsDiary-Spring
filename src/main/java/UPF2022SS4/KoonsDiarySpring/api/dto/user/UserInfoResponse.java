package UPF2022SS4.KoonsDiarySpring.api.dto.user;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
@Builder @Getter
public class UserInfoResponse {
    private String userName;

    private String email;

    private String nickName;
}
