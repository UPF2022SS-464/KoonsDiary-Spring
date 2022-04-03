package UPF2022SS4.KoonsDiarySpring.service.user;


import UPF2022SS4.KoonsDiarySpring.domain.User;
import UPF2022SS4.KoonsDiarySpring.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class PatchUserTest {


    @Autowired
    private UserService userService;

    //유저 닉네임 수정에 대한 테스트
    @Test
    public void update_nickname_success() throws Exception{
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

        System.out.println("변경 전 닉네임 = " + user1.getNickname());
        userService.updateUser(user1, "잉쿤2");
        System.out.println("변경 이후 닉네임 = " + user1.getNickname());
    }

    //유저 닉네임 수정에 대한 테스트
    @Test
    public void update_nickname_fail() throws Exception{
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

//        userService.join(user1);
//        userService.join(user2);

        System.out.println("변경 전 닉네임 = " + user1.getNickname());
        userService.updateUser(user1, "잉쿤");
        System.out.println("변경 이후 닉네임 = " + user1.getNickname());
    }
}
