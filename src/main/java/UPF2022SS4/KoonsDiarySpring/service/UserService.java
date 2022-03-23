package UPF2022SS4.KoonsDiarySpring.service;

import UPF2022SS4.KoonsDiarySpring.api.dto.DefaultResponse;
import UPF2022SS4.KoonsDiarySpring.domain.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    public DefaultResponse join(User user);
    public String logIn(String userId, String pwd);

}
