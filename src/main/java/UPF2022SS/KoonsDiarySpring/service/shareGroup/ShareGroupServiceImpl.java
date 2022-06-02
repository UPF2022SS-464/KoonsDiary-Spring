package UPF2022SS.KoonsDiarySpring.service.shareGroup;

import UPF2022SS.KoonsDiarySpring.domain.ShareGroup;
import UPF2022SS.KoonsDiarySpring.domain.User;
import UPF2022SS.KoonsDiarySpring.repository.shareGroup.ShareGroupJpaRepository;
import UPF2022SS.KoonsDiarySpring.repository.user.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor()
public class ShareGroupServiceImpl implements ShareGroupService{


    private final UserJpaRepository userJpaRepository;
    private final ShareGroupJpaRepository shareGroupJpaRepository;

    @Override
    public ShareGroup createShareGroup(Long userId, String shareGroupName, String ImagePath) {

        User user = userJpaRepository.findById(userId).get();

        ShareGroup shareGroup = ShareGroup.builder()
                .user(user)
                .shareGroupName(shareGroupName)
                .groupImagePath(ImagePath).build();
         shareGroupJpaRepository.save(shareGroup);

        return shareGroup;
    }

    @Override
    public List<ShareGroup> getShareGroup(Long userId) {
        return null;
    }

    @Override
    public ShareGroup updateShareGroup(Long userId, String shareGroupName, String ImagePath) {
        return null;
    }

    @Override
    public void deleteShareGroup(Long shareGroupId) {

    }
}
