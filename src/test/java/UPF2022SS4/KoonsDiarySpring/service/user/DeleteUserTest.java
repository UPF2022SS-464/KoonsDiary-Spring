package UPF2022SS4.KoonsDiarySpring.service.user;

import UPF2022SS4.KoonsDiarySpring.common.ResponseMessage;
import UPF2022SS4.KoonsDiarySpring.common.StatusCode;
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
public class DeleteUserTest {

    @Autowired
    private UserService userService;

    @Test
    public void delete_user_success() throws Exception{

        //given
        User user1 = User.builder()
                .username("dldlswo97220")
                .password("cucumber52")
                .nickname("잉쿤")
                .email("test1@gmail.com")
                .build();
        userService.join(user1);

        // when
        userService.deleteUser(user1.getId());

        //then
        System.out.println(StatusCode.OK + ", " + ResponseMessage.USER_DELETE_SUCCESS);

//        mockMvc.getDispatcherServlet();
    }

    @Test
    public void delete_user_fail() throws Exception{

        //given
        User user1 = User.builder()
                .username("dldlswo97220")
                .password("cucumber52")
                .nickname("잉쿤")
                .email("test1@gmail.com")
                .build();
//        userService.join(user1);


        try {
            // when
            userService.deleteUser(user1.getId());
        }
        catch (Exception e){
            //then
            System.out.println("Error: " + e.getMessage());
            System.out.println(StatusCode.CONFLICT + ", " + ResponseMessage.USER_DELETE_FAIL);
        }
    }
}
