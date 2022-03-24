package UPF2022SS4.KoonsDiarySpring.service;

import UPF2022SS4.KoonsDiarySpring.api.dto.DefaultResponse;
import UPF2022SS4.KoonsDiarySpring.domain.User;
import UPF2022SS4.KoonsDiarySpring.repository.UserJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class UserServiceImplTest {

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private UserService userService;

    @Test
    public void right_signUp_success() throws Exception{
        User user = User.builder()
                .username("dldlswo97220")
                .password("cucumber52")
                .nickname("잉쿤")
                .email("test@gmail.com")
                .build();

        DefaultResponse dr = userService.join(user);
        System.out.println("dr = " + dr.getStatus() + dr.getMessage());
    }
    @Test
    public void right_signUp_fail() throws Exception{
        User user = User.builder()
                .username(null)
                .password(null)
                .nickname(null)
                .email(null)
                .build();

        DefaultResponse dr = userService.join(user);
        System.out.println("dr = " + dr.getStatus() + dr.getMessage());
    }

    @Test
    public void right_signUp_duplicateId() throws Exception{
        User user1 = User.builder()
                .username("dldlswo97220")
                .password("cucumber52")
                .nickname("잉쿤")
                .email("test1@gmail.com")
                .build();

        User user2 = User.builder()
                .username("dldlswo97220")
                .password("cucumber52")
                .nickname("잉쿤")
                .email("test2@gmail.com")
                .build();

        DefaultResponse dr1 =  userService.join(user1);
        DefaultResponse dr2 =  userService.join(user2);
        System.out.println("dr1 = " + dr1);
        System.out.println("dr2 = " + dr2);
    }
}