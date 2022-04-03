package UPF2022SS4.KoonsDiarySpring.service.user;


import UPF2022SS4.KoonsDiarySpring.domain.User;
import UPF2022SS4.KoonsDiarySpring.service.AuthService;
import UPF2022SS4.KoonsDiarySpring.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
public class GetUserTest {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    // 유저 데이터 조회 성공
    @Test
    public void get_userdata_success() throws Exception{
        User user1 = User.builder()
                .username("dldlswo97220")
                .password("cucumber52")
                .nickname("잉쿤")
                .email("test1@gmail.com")
                .build();

    }

    //유저 데이터 조회 실패
    @Test
    public void get_userdata_fail() throws Exception{
        User user1 = User.builder()
                .username("dldlswo97220")
                .password("cucumber52")
                .nickname("잉쿤")
                .email("test1@gmail.com")
                .build();
    }

}
