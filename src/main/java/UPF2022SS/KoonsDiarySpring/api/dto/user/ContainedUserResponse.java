package UPF2022SS.KoonsDiarySpring.api.dto.user;


import UPF2022SS.KoonsDiarySpring.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder()
public class ContainedUserResponse {
    List<User> users;
}
