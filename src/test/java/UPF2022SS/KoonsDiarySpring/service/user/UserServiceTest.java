package UPF2022SS.KoonsDiarySpring.service.user;
import UPF2022SS.KoonsDiarySpring.api.dto.user.ContainedUserRequest;
import UPF2022SS.KoonsDiarySpring.api.dto.user.ContainedUserResponse;
import UPF2022SS.KoonsDiarySpring.domain.ImagePath;
import UPF2022SS.KoonsDiarySpring.domain.User;
import UPF2022SS.KoonsDiarySpring.service.AuthService;
import UPF2022SS.KoonsDiarySpring.service.JwtService;
import UPF2022SS.KoonsDiarySpring.service.image.ImageService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

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
    @Autowired
    private ImageService imageService;
    @Autowired
    private EntityManager em;

    @Test
    void join_user_success() {

        String path = "profilePath1";
        ImagePath imagePath = ImagePath.builder().path(path).build();

        imageService.createImage(imagePath);



        User user = User.builder()
                .username("test")
                .userPwd("cucumber52")
                .email("test@gmail.com")
                .nickname("test")
                .imagePath(imagePath)
                .build();

        ResponseEntity response = userService.join(user);
        System.out.println("response = " + response);
    }

    @Test
    void findUsers() {
        String path = "profilePath1";
        ImagePath imagePath = ImagePath.builder().path(path).build();

        imageService.createImage(imagePath);

        User user = User.builder()
                .username("test")
                .userPwd("cucumber52")
                .email("test@gmail.com")
                .nickname("test")
                .imagePath(imagePath)
                .build();

        ResponseEntity response = userService.join(user);
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
        String path = "profilePath1";
        ImagePath imagePath = ImagePath.builder().path(path).build();

        imageService.createImage(imagePath);



        User user = User.builder()
                .username("test")
                .userPwd("cucumber52")
                .email("test@gmail.com")
                .nickname("test")
                .imagePath(imagePath)
                .build();


        ResponseEntity response = userService.join(user);
        System.out.println("response = " + response);

        User username = userService.findUsername(user.getNickname());
        System.out.println("username = " + username);
    }

    @Test
    void findUserEmail() {
        String path = "profilePath1";
        ImagePath imagePath = ImagePath.builder().path(path).build();

        imageService.createImage(imagePath);



        User user = User.builder()
                .username("test")
                .userPwd("cucumber52")
                .email("test@gmail.com")
                .nickname("test")
                .imagePath(imagePath)
                .build();


        ResponseEntity response = userService.join(user);
        User findUser = userService.findUserEmail(user.getEmail());

        System.out.println("username = " + findUser);
    }

    @Test
    void updateUser() {
        String path = "profilePath1";
        ImagePath imagePath = ImagePath.builder().path(path).build();

        imageService.createImage(imagePath);



        User user = User.builder()
                .username("test")
                .userPwd("cucumber52")
                .email("test@gmail.com")
                .nickname("test")
                .imagePath(imagePath)
                .build();


        ResponseEntity response = userService.join(user);

        User findUser = userService.findUsername(user.getUsername());
        String nickName= "orly";
        findUser.updateNickname(nickName);
        Assertions.assertThat(nickName).isEqualTo(findUser.getNickname());
        System.out.println("findUser.getNickname() = " + findUser.getNickname());
    }

    @Test
    void deleteUser() {
        String path = "profilePath1";
        ImagePath imagePath = ImagePath.builder().path(path).build();

        imageService.createImage(imagePath);



        User user = User.builder()
                .username("test")
                .userPwd("cucumber52")
                .email("test@gmail.com")
                .nickname("test")
                .imagePath(imagePath)
                .build();


        ResponseEntity response = userService.join(user);
        User findUser = userService.findUsername(user.getUsername());


        userService.deleteUser(findUser.getId());
    }

    @Test
    void findByContainedText_success(){
        String path1 = "profilePath1";
        ImagePath imagePath1 = ImagePath.builder().path(path1).build();
        String path2 = "profilePath2";
        ImagePath imagePath2 = ImagePath.builder().path(path2).build();

        imageService.createImage(imagePath1);
        imageService.createImage(imagePath2);



        User user1 = User.builder()
                .username("test1")
                .userPwd("cucumber52")
                .email("test1@gmail.com")
                .nickname("test1")
                .imagePath(imagePath1)
                .build();


        ResponseEntity response1 = userService.join(user1);

        User user2 = User.builder()
                .username("test2")
                .userPwd("cucumber52")
                .email("test2@gmail.com")
                .nickname("test2")
                .imagePath(imagePath2)
                .build();

        ResponseEntity response2 = userService.join(user2);

        ContainedUserRequest cur = new ContainedUserRequest("te");

        ContainedUserResponse json = userService.findByContainedUser(cur);
        System.out.println("json = " + json.getUserListJsonData());
    }
}