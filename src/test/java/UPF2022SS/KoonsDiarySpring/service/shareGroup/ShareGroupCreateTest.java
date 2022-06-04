package UPF2022SS.KoonsDiarySpring.service.shareGroup;

import UPF2022SS.KoonsDiarySpring.domain.ImagePath;
import UPF2022SS.KoonsDiarySpring.domain.ShareGroup;
import UPF2022SS.KoonsDiarySpring.domain.User;
import UPF2022SS.KoonsDiarySpring.repository.image.ImageJpaRepository;
import UPF2022SS.KoonsDiarySpring.repository.shareGroup.ShareGroupJpaRepository;
import UPF2022SS.KoonsDiarySpring.repository.user.UserJpaRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ShareGroupCreateTest {


    @Mock
    private UserJpaRepository userJpaRepository;

    @Mock
    private ShareGroupJpaRepository shareGroupJpaRepository;

    @Mock
    private ImageJpaRepository imageJpaRepository;

    @InjectMocks
    private ShareGroupServiceImpl shareGroupService;

    @Test
    @DisplayName("공유 일기장 생성 [성공]")
    void createShareGroup_Success() {
        //given
        String shareGroupName = "testShareGroup";
        String shareGroupImagePath = "testShareGroupImagePath";

        User user = init();

        //when
        ShareGroup shareGroup = shareGroupService.createShareGroup(user, shareGroupName, shareGroupImagePath);

        //then
        Assertions.assertThat(shareGroup).isNotNull();
    }

    @Test
    @DisplayName("공유 일기장 생성 [실패]")
    void createShareGroup_Fail(){
        //given
        String shareGroupName = "testShareGroup";
        String shareGroupImagePath = "testShareGroupImagePath";

        User user = init();

        //when
        ShareGroup shareGroup = shareGroupService.createShareGroup(user, shareGroupName, shareGroupImagePath);

        //then
        Assertions.assertThat(shareGroup).isNotNull();
    }

    @Transactional
    public User init(){

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
        return user;
    }
}