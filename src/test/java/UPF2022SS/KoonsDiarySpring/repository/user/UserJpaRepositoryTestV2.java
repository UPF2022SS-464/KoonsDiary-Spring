package UPF2022SS.KoonsDiarySpring.repository.user;

import UPF2022SS.KoonsDiarySpring.domain.ImagePath;
import UPF2022SS.KoonsDiarySpring.domain.User;
import UPF2022SS.KoonsDiarySpring.repository.image.ImageJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@Transactional
@ExtendWith(SpringExtension.class)
public class UserJpaRepositoryTestV2 {

    @Autowired
    private UserJpaRepository userJpaRepository;
    @Autowired
    private ImageJpaRepository imageJpaRepository;

    @Test
    void getByUsername_user_success(){
        ImagePath imagePath = setImage();
        User user = User.builder()
                .username("test")
                .password("cucumber52")
                .email("test@gmail.com")
                .nickname("test")
                .imagePath(imagePath)
                .build();

        userJpaRepository.save(user);
        System.out.println("user.getId() = " + user.getId());
        User findUser = userJpaRepository.findById(user.getId()).get();
        assertEquals(user, findUser);
    }

    @Test
    void getByUsername_user_success_V2(){
        ImagePath imagePath = setImage();
        User user = User.builder()
                .username("koon")
                .password("cucumber52")
                .email("koon@gmail.com")
                .nickname("koon")
                .imagePath(imagePath)
                .build();

        userJpaRepository.save(user);
        User findUser = userJpaRepository.findByUsername(user.getUsername()).get();
        assertThat(findUser.getId()).isEqualTo(user.getId());
        System.out.println("findUser = " + findUser.getUsername() + " " + findUser.getNickname());
    }

    @Test
    void getByUserEmail_user_success() throws Exception{
        ImagePath imagePath = setImage();
        User user = User.builder()
                .username("koon")
                .password("cucumber52")
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
                .password("cucumber52")
                .email("koon@gmail.com")
                .nickname("koon")
                .imagePath(imagePath)
                .build();

        User user2 = User.builder()
                .username("test123")
                .password("cucumber52")
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
