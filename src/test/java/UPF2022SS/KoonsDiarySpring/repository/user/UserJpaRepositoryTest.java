package UPF2022SS.KoonsDiarySpring.repository.user;

import UPF2022SS.KoonsDiarySpring.QuerydslConfig;
import UPF2022SS.KoonsDiarySpring.domain.ImagePath;
import UPF2022SS.KoonsDiarySpring.domain.User;
import UPF2022SS.KoonsDiarySpring.repository.image.ImageJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(showSql = false)
@Import(QuerydslConfig.class)
class UserJpaRepositoryTest {

    @Autowired
    private UserJpaRepository userJpaRepository;
    @Autowired
    private ImageJpaRepository imageJpaRepository;

    @Test
    void getByUsername_user_success(){
        ImagePath imagePath = setImage();
        User user = User.builder()
                .username("koon")
                .userPwd("cucumber52")
                .email("koon@gmail.com")
                .nickname("koon")
                .imagePath(imagePath)
                .build();

        userJpaRepository.save(user);
        System.out.println("user.getId() = " + user.getId());
        User findUser = userJpaRepository.findById(user.getId()).get();
        assertEquals(user, findUser);
        System.out.println("user = " + user.getId() + " findUser = " + findUser.getId());
    }

    @Test
    void getByUsername_user_success_V2(){
        ImagePath imagePath = setImage();
        User user = User.builder()
                .username("koon")
                .userPwd("cucumber52")
                .email("koon@gmail.com")
                .nickname("koon")
                .imagePath(imagePath)
                .build();

        userJpaRepository.save(user);
        User findUser = userJpaRepository.findByName(user.getUsername());
        assertThat(findUser.getId()).isEqualTo(user.getId());
        System.out.println("findUser = " + findUser.getUsername() + " " + findUser.getNickname());
    }

    @Test
    void getByUserEmail_user_success() throws Exception{
        ImagePath imagePath = setImage();
        User user = User.builder()
                .username("koon")
                .userPwd("cucumber52")
                .email("koon@gmail.com")
                .nickname("koon")
                .imagePath(imagePath)
                .build();

        userJpaRepository.save(user);

        User findUser = userJpaRepository.findByEmail(user.getEmail()).get();
        assertThat(findUser.getId()).isEqualTo(user.getId());
        System.out.println("findUser = " + findUser.getUsername() + " " + findUser.getNickname());
    }

    @Test
    void findAll_user_success(){
        ImagePath imagePath = setImage();
        User user = User.builder()
                .username("koon")
                .userPwd("cucumber52")
                .email("koon@gmail.com")
                .nickname("koon")
                .imagePath(imagePath)
                .build();

        User user2 = User.builder()
                .username("test123")
                .userPwd("cucumber52")
                .email("test123@gmail.com")
                .nickname("test123")
                .imagePath(imagePath)
                .build();

        userJpaRepository.save(user);
        userJpaRepository.save(user2);

        List<User> users = userJpaRepository.findAll();

        for (User user1 : users) {
            System.out.println("user = " + user1);
        }
    }

    public ImagePath setImage(){
        ImagePath imagePath = ImagePath.builder().path("profile1").build();
        imageJpaRepository.save(imagePath);
        return imagePath;
    }
}