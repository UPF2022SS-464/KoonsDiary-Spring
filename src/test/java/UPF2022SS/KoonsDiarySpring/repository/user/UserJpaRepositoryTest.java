package UPF2022SS.KoonsDiarySpring.repository.user;

import UPF2022SS.KoonsDiarySpring.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@Transactional
@SpringBootTest
@ExtendWith(SpringExtension.class)
class UserJpaRepositoryTest {

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void getByUsername_user_success(){
        User user = User.builder()
                .username("koon")
                .password("cucumber52")
                .email("koon@gmail.com")
                .nickname("koon")
                .imagePath("imagePath1")
                .build();

        userJpaRepository.save(user);
        User findUser = userJpaRepository.findByName(user.getUsername());
        assertThat(findUser.getId()).isEqualTo(user.getId());
        System.out.println("findUser = " + findUser.getUsername() + " " + findUser.getNickname());
    }

    @Test
    void getByUsername_user_success_V2(){
        User user = User.builder()
                .username("koon")
                .password(passwordEncoder.encode("cucumber52"))
                .email("koon@gmail.com")
                .nickname("koon")
                .imagePath("imagePath1")
                .build();

        userJpaRepository.save(user);
        User findUser = userJpaRepository.findByName(user.getUsername());
        assertThat(findUser.getId()).isEqualTo(user.getId());
        System.out.println("findUser = " + findUser.getUsername() + " " + findUser.getNickname());
    }

    @Test
    void getByUserEmail_user_success() throws Exception{
        User user = User.builder()
                .username("koon")
                .password("cucumber52")
                .email("koon@gmail.com")
                .nickname("koon")
                .imagePath("imagePath1")
                .build();

        userJpaRepository.save(user);

        User findUser = userJpaRepository.findByEmail(user.getEmail());
        assertThat(findUser.getId()).isEqualTo(user.getId());
        System.out.println("findUser = " + findUser.getUsername() + " " + findUser.getNickname());
    }

    @Test
    void findAll_user_success(){
        User user = User.builder()
                .username("koon")
                .password("cucumber52")
                .email("koon@gmail.com")
                .nickname("koon")
                .imagePath("imagePath1")
                .build();

        User user2 = User.builder()
                .username("test123")
                .password("cucumber52")
                .email("test123@gmail.com")
                .nickname("test123")
                .imagePath("imagePath1")
                .build();

        userJpaRepository.save(user);
        userJpaRepository.save(user2);

        List<User> users = userJpaRepository.findAll();

        for (User user1 : users) {
            System.out.println("user = " + user1);
        }
    }
}