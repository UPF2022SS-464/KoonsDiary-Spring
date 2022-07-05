package UPF2022SS.KoonsDiarySpring.service.user;

import UPF2022SS.KoonsDiarySpring.api.dto.user.ContainedUserRequest;
import UPF2022SS.KoonsDiarySpring.api.dto.user.ContainedUserResponse;
import UPF2022SS.KoonsDiarySpring.api.dto.user.Kakao;
import UPF2022SS.KoonsDiarySpring.api.dto.user.UpdateUser;
import UPF2022SS.KoonsDiarySpring.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    public User join(User user);
    public ResponseEntity<Kakao.AccessDto> getKakaoId(String code);

    public List<User> findUsers();
    public User findById(Long id);
    public User findUsername(String username);
    public User findUserEmail(String email);
    public User findUserKakaoId(Long kakaoId);
    public ContainedUserResponse findByContainedUser(ContainedUserRequest cur);
    public void update(User user, UpdateUser.Request request);
    public void delete(Long id);


    public boolean validateDuplicateUserId(String userId);
    public boolean validateDuplicateUserEmail(String userEmail);

//    public DefaultResponse login(LoginRequest loginRequest);

}
