package UPF2022SS4.KoonsDiarySpring.service;

import UPF2022SS4.KoonsDiarySpring.api.dto.DefaultResponse;
import UPF2022SS4.KoonsDiarySpring.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    public DefaultResponse join(User user);
    public List<User> findUsers();
    public User findUsername(String username);
    public User findUserEmail(String email);

}
