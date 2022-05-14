package UPF2022SS.KoonsDiarySpring.service.user;

import UPF2022SS.KoonsDiarySpring.api.dto.DefaultResponse;
import UPF2022SS.KoonsDiarySpring.api.dto.user.ContainedUserRequest;
import UPF2022SS.KoonsDiarySpring.api.dto.user.ContainedUserResponse;
import UPF2022SS.KoonsDiarySpring.api.dto.user.SignUpRequest;
import UPF2022SS.KoonsDiarySpring.api.dto.user.UpdateUser;
import UPF2022SS.KoonsDiarySpring.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    public DefaultResponse join(User user);
    public DefaultResponse kakaoJoin(User user);
    public List<User> findUsers();
    public User findById(Long id);
    public User findUsername(String username);
    public User findUserEmail(String email);
    public ContainedUserResponse findByContainedUser(ContainedUserRequest cur);
    public void updateUser(User user, UpdateUser.Request request);
    public void deleteUser(Long id);
//    public DefaultResponse login(LoginRequest loginRequest);

}
