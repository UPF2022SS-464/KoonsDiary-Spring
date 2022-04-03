package UPF2022SS4.KoonsDiarySpring.api.dto.user;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateUserRequest {
    private String nickname;
}
