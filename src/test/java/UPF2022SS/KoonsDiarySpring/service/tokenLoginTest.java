package UPF2022SS.KoonsDiarySpring.service;

import UPF2022SS.KoonsDiarySpring.domain.ImagePath;
import UPF2022SS.KoonsDiarySpring.domain.RefreshToken;
import UPF2022SS.KoonsDiarySpring.domain.User;
import UPF2022SS.KoonsDiarySpring.service.image.ImageService;
import UPF2022SS.KoonsDiarySpring.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

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
    @Autowired
    private ImageService imageService;

    @Test
    void tokenLogin() {


        ImagePath imagePath = setImage();
        User user = User.builder()
                .username("test")
                .email("test@gmail.com")
                .password("cucumber52")
                .nickname("test")
                .imagePath(imagePath)
                .build();
        RefreshToken token = new RefreshToken(user, authService.createRefreshToken());
        userService.join(user);

        user.setRefreshToken(token);

//        em.flush();

        System.out.println("user.getId() = " + user.getId());
        System.out.println("user.getRefreshToken() = " + user.getRefreshToken().getValue());

        ResponseEntity response = authService.tokenLogin(user.getRefreshToken().getValue());

        System.out.println("response = " + response);

    }

    public ImagePath setImage(){
        ImagePath imagePath = ImagePath.builder().path("profile1").build();
        imageService.createImage(imagePath);
        return imagePath;
    }
}