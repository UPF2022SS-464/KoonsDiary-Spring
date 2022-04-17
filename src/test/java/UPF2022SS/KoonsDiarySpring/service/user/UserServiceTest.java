package UPF2022SS.KoonsDiarySpring.service.user;
import UPF2022SS.KoonsDiarySpring.api.dto.DefaultResponse;
import UPF2022SS.KoonsDiarySpring.api.dto.user.ContainedUserRequest;
import UPF2022SS.KoonsDiarySpring.api.dto.user.ContainedUserResponse;
import UPF2022SS.KoonsDiarySpring.domain.User;
import UPF2022SS.KoonsDiarySpring.repository.user.UserJpaRepository;
import UPF2022SS.KoonsDiarySpring.service.AuthService;
import UPF2022SS.KoonsDiarySpring.service.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@ExtendWith(SpringExtension.class)
class UserServiceTest {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthService authService;

    @Test
    void join_user_success() {
        User user = User.builder()
                .username("test")
                .password("cucumber52")
                .email("test@gmail.com")
                .nickname("test")
                .build();

        DefaultResponse response = userService.join(user);
        System.out.println("response = " + response);
    }

    @Test
    void findUsers() {
        User user = User.builder()
                .username("test")
                .password("cucumber52")
                .email("test@gmail.com")
                .nickname("test")
                .build();

        DefaultResponse response = userService.join(user);
        List<User> users = userService.findUsers();
        for (User userObj : users) {
            System.out.println("user = " + userObj);
        }
    }

    @Test
    void findById() {

    }

    @Test
    void findUsername() {
        User user = User.builder()
                .username("test")
                .password("cucumber52")
                .email("test@gmail.com")
                .nickname("test")
                .build();

        userService.join(user);
        User username = userService.findUsername(user.getUsername());

        System.out.println("username = " + username);
    }

    @Test
    void findUserEmail() {
        User user = User.builder()
                .username("test")
                .password("cucumber52")
                .email("test@gmail.com")
                .nickname("test")
                .build();

        userService.join(user);
        User findUser = userService.findUserEmail(user.getEmail());

        System.out.println("username = " + findUser);
    }

    @Test
    void updateUser() {
        User user = User.builder()
                .username("test")
                .password("cucumber52")
                .email("test@gmail.com")
                .nickname("test")
                .build();
        userService.join(user);
        User findUser = userService.findUsername(user.getUsername());

        String nickName= "orly";
        findUser.updateNickname(nickName);
    }

    @Test
    void deleteUser() {
        User user = User.builder()
                .username("test")
                .password("cucumber52")
                .email("test@gmail.com")
                .nickname("test")
                .build();
        userService.join(user);
        User findUser = userService.findUsername(user.getUsername());

        userService.deleteUser(findUser.getId());
    }

    @Test
    void findByContainedText_success(){
        User user1 = User.builder()
                .username("test1")
                .password("cucumber52")
                .email("test1@gmail.com")
                .nickname("test1")
                .build();
        userService.join(user1);

        User user2 = User.builder()
                .username("test2")
                .password("cucumber52")
                .email("test2@gmail.com")
                .nickname("test2")
                .build();
        userService.join(user2);

        ContainedUserRequest cur = new ContainedUserRequest("te");

        ContainedUserResponse json = userService.findByContainedUser(cur);
        System.out.println("json = " + json.getUserListJsonData());
    }
}