package UPF2022SS.KoonsDiarySpring.repository.shareGroup;

import UPF2022SS.KoonsDiarySpring.QuerydslConfig;
import UPF2022SS.KoonsDiarySpring.domain.Enum.Authority;
import UPF2022SS.KoonsDiarySpring.domain.ImagePath;
import UPF2022SS.KoonsDiarySpring.domain.ShareGroup;
import UPF2022SS.KoonsDiarySpring.domain.ShareGroupUser;
import UPF2022SS.KoonsDiarySpring.domain.User;
import UPF2022SS.KoonsDiarySpring.repository.image.ImageJpaRepository;
import UPF2022SS.KoonsDiarySpring.repository.shareGroup.user.ShareGroupUserJpaRepository;
import UPF2022SS.KoonsDiarySpring.repository.user.UserJpaRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@DataJpaTest
@Import(QuerydslConfig.class)
@ExtendWith(SpringExtension.class)
class ShareGroupRepositoryTest {

    @Autowired
    private ShareGroupJpaRepository shareGroupJpaRepository;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private ShareGroupUserJpaRepository shareGroupUserJpaRepository;

    @Autowired
    private ImageJpaRepository imageJpaRepository;

    @BeforeEach
    void init(){
        ImagePath imagePath = ImagePath.builder().path("testImagePath").build();
        imageJpaRepository.save(imagePath);

        User user = User.builder()
                .username("test")
                .password("cucumber52")
                .nickname("test")
                .imagePath(imagePath)
                .build();

        userJpaRepository.save(user);
    }

    @Test
    @DisplayName(value = "그룹 생성 및 조회 기능[성공]")
    void findListAll_success() {
        User user = userJpaRepository.findByName("test");
        ShareGroup shareGroup = ShareGroup.builder()
                .shareGroupName("testShareGroup")
                .shareGroupImagePath("testShareGroupImagePath")
                .build();

        shareGroupJpaRepository.save(shareGroup);

        ShareGroupUser shareGroupUser = ShareGroupUser.builder().user(user).shareGroup(shareGroup).authority(Authority.ADMIN).build();
        shareGroupUserJpaRepository.save(shareGroupUser);

        List<ShareGroup> shareGroupList = shareGroupUserJpaRepository.findByUser(user).get();
        Assertions.assertThat(shareGroupList).isNotEmpty();
        for (ShareGroup group : shareGroupList) {
            System.out.println("group.getShareGroupName() = " + group.getShareGroupName());
        }
    }

    @Test
    @DisplayName(value = "그룹 생성 및 조회 기능[실패]")
    void findListAll_false() {
        User user = userJpaRepository.findByName("test");
        ShareGroup shareGroup = ShareGroup.builder()
                .shareGroupName("testShareGroup")
                .shareGroupImagePath("testShareGroupImagePath")
                .build();

//        shareGroupJpaRepository.save(shareGroup);

        List<ShareGroup> shareGroupList = shareGroupUserJpaRepository.findByUser(user).get();
        Assertions.assertThat(shareGroupList).isEmpty();
        System.out.println("비어있는 배열입니다.");
    }
}