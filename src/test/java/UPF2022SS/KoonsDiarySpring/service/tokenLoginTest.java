package UPF2022SS.KoonsDiarySpring.service;

import UPF2022SS.KoonsDiarySpring.api.dto.DefaultResponse;
import UPF2022SS.KoonsDiarySpring.domain.RefreshToken;
import UPF2022SS.KoonsDiarySpring.domain.User;
import UPF2022SS.KoonsDiarySpring.repository.user.UserJpaRepository;
import UPF2022SS.KoonsDiarySpring.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@ExtendWith(SpringExtension.class)
class tokenLoginTest {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;
    @Autowired
    private EntityManager em;

    @Test
    void tokenLogin() {
        User user = User.builder()
                .username("test")
                .email("test@gmail.com")
                .password("cucumber52")
                .nickname("test")
                .imagePath("imagePath1")
                .build();
        RefreshToken token = new RefreshToken(user, authService.createRefreshToken());
        userService.join(user);

        user.setRefreshToken(token);

//        em.flush();

        System.out.println("user.getId() = " + user.getId());
        System.out.println("user.getRefreshToken() = " + user.getRefreshToken().getValue());

        DefaultResponse response = authService.tokenLogin(user.getRefreshToken().getValue());

        System.out.println("response = " + response);

    }
}