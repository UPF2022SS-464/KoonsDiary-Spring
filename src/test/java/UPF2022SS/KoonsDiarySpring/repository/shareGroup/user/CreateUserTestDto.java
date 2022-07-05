package UPF2022SS.KoonsDiarySpring.repository.shareGroup.user;


import UPF2022SS.KoonsDiarySpring.QuerydslConfig;
import UPF2022SS.KoonsDiarySpring.domain.Enum.Authority;
import UPF2022SS.KoonsDiarySpring.domain.ImagePath;
import UPF2022SS.KoonsDiarySpring.domain.ShareGroup;
import UPF2022SS.KoonsDiarySpring.domain.ShareGroupUser;
import UPF2022SS.KoonsDiarySpring.domain.User;
import UPF2022SS.KoonsDiarySpring.repository.image.ImageJpaRepository;
import UPF2022SS.KoonsDiarySpring.repository.shareGroup.ShareGroupJpaRepository;
import UPF2022SS.KoonsDiarySpring.repository.shareGroup.invite.ShareGroupInviteJpaRepository;
import UPF2022SS.KoonsDiarySpring.repository.user.UserJpaRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(QuerydslConfig.class)
@ExtendWith(SpringExtension.class)
public class CreateUserTestDto {

    @Autowired
    private UserJpaRepository userJpaRepository;
    @Autowired
    private ImageJpaRepository imageJpaRepository;

    @Autowired
    private ShareGroupJpaRepository shareGroupJpaRepository;

    @Autowired
    private ShareGroupInviteJpaRepository shareGroupInviteJpaRepository;

    @Autowired
    private ShareGroupUserJpaRepository shareGroupUserJpaRepository;

    @BeforeEach
    void init(){
        ImagePath imagePath = ImagePath.builder().path("testImagePath").build();
        imageJpaRepository.save(imagePath);

        User user = User.builder()
                .id(1004L)
                .username("test")
                .password("cucumber52")
                .nickname("test")
                .imagePath(imagePath)
                .build();

        userJpaRepository.save(user);
        System.out.println("user = " + user.getId());
        ShareGroup shareGroup= ShareGroup.builder()
                .shareGroupImagePath("shareGroupImagePath.png")
                .shareGroupName("shareGroupName")
                .id(1004L).build();

        shareGroupJpaRepository.save(shareGroup);
    }



    @Test
    @DisplayName("공유일기 유저 생성")
    void createUser() throws JsonProcessingException {

        Optional<User> user = userJpaRepository.findByUsername("test");
        Optional<ShareGroup> shareGroup = shareGroupJpaRepository.findByShareGroupName("shareGroupName");

        ShareGroupUser shareGroupUser = ShareGroupUser.builder()
                .user(user.get())
                .shareGroup(shareGroup.get())
                .authority(Authority.ADMIN).build();

        shareGroupUserJpaRepository.save(shareGroupUser);

        assertThat(shareGroupUser).isNotNull();

        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(shareGroupUser);
        System.out.println(json);
    }
}
