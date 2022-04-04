package UPF2022SS4.KoonsDiarySpring.api.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder()
public class DeleteUserRequest {
    private Long userId;
}
