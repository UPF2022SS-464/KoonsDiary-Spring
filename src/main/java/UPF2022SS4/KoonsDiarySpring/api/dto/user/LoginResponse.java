package UPF2022SS4.KoonsDiarySpring.api.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;


//jwt 응답을 위한 DTO
@Data
@AllArgsConstructor
public class LoginResponse {

    private Long id;

    private String token;

    private String userId;


}
