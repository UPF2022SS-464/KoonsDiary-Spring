package UPF2022SS.KoonsDiarySpring.service.user;
import UPF2022SS.KoonsDiarySpring.api.dto.user.ContainedUserRequest;
import UPF2022SS.KoonsDiarySpring.api.dto.user.ContainedUserResponse;
import UPF2022SS.KoonsDiarySpring.domain.ImagePath;
import UPF2022SS.KoonsDiarySpring.domain.User;
import UPF2022SS.KoonsDiarySpring.repository.user.UserJpaRepository;
import UPF2022SS.KoonsDiarySpring.service.AuthService;
import UPF2022SS.KoonsDiarySpring.service.JwtService;
import UPF2022SS.KoonsDiarySpring.service.image.ImageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
@ExtendWith(SpringExtension.class)
class UserDtoServiceTest {

    @Autowired
    private UserJpaRepository userJpaRepository;

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
                .password("cucumber52")
                .email("test@gmail.com")
                .nickname("test")
                .imagePath(imagePath)
                .build();

        User saveUser = userService.join(user);
        assertThat(user).isEqualTo(saveUser);
        System.out.println("response = " + saveUser.getUsername());
    }

    @Test
    void findUsers() {
        String path = "profilePath1";
        ImagePath imagePath = ImagePath.builder().path(path).build();

        imageService.createImage(imagePath);

        User user = User.builder()
                .username("test")
                .password("cucumber52")
                .email("test@gmail.com")
                .nickname("test")
                .imagePath(imagePath)
                .build();

        userService.join(user);
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
                .password("cucumber52")
                .email("test@gmail.com")
                .nickname("test")
                .imagePath(imagePath)
                .build();


       user = userService.join(user);

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
                .password("cucumber52")
                .email("test@gmail.com")
                .nickname("test")
                .imagePath(imagePath)
                .build();


        user = userService.join(user);
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
                .password("cucumber52")
                .email("test@gmail.com")
                .nickname("test")
                .imagePath(imagePath)
                .build();


        userJpaRepository.save(user);

        String nickName= "orly";
        user.updateNickname(nickName);
        assertThat(nickName).isEqualTo(user.getNickname());
        System.out.println("findUser.getNickname() = " + user.getNickname());
    }

    @Test
    void deleteUser() {
        String path = "profilePath1";
        ImagePath imagePath = ImagePath.builder().path(path).build();

        imageService.createImage(imagePath);

        User user = User.builder()
                .username("test")
                .password("cucumber52")
                .email("test@gmail.com")
                .nickname("test")
                .imagePath(imagePath)
                .build();


        userJpaRepository.save(user);


        userService.delete(user.getId());
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
                .password("cucumber52")
                .email("test1@gmail.com")
                .nickname("test1")
                .imagePath(imagePath1)
                .build();


        user1 = userService.join(user1);

        User user2 = User.builder()
                .username("test2")
                .password("cucumber52")
                .email("test2@gmail.com")
                .nickname("test2")
                .imagePath(imagePath2)
                .build();

        user2 = userService.join(user2);

        ContainedUserRequest cur = new ContainedUserRequest("te");

        ContainedUserResponse json = userService.findByContainedUser(cur);
        System.out.println("json = " + json.getUserListJsonData());
    }
}