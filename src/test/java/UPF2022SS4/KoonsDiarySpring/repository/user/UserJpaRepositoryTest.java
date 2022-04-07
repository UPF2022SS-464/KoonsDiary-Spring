package UPF2022SS4.KoonsDiarySpring.repository.user;

import UPF2022SS4.KoonsDiarySpring.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@Transactional
@SpringBootTest
@ExtendWith(SpringExtension.class)
class UserJpaRepositoryTest {

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Test
    void getByUsername_user_success(){
        User user = User.builder()
                .username("test")
                .password("cucumber52")
                .email("test@gmail.com")
                .nickname("test")
                .build();

        userJpaRepository.save(user);

        User findUser = userJpaRepository.findByName(user.getUsername());
        assertThat(findUser.getId()).isEqualTo(user.getId());
        System.out.println("findUser = " + findUser.getUsername() + " " + findUser.getNickname());
    }
}