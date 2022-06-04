package UPF2022SS.KoonsDiarySpring.service.shareGroup;

import UPF2022SS.KoonsDiarySpring.domain.ShareGroup;
import UPF2022SS.KoonsDiarySpring.domain.User;
import UPF2022SS.KoonsDiarySpring.repository.shareGroup.ShareGroupJpaRepository;
import UPF2022SS.KoonsDiarySpring.repository.user.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static UPF2022SS.KoonsDiarySpring.api.dto.shareGroup.ShareGroup.*;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor()
public class ShareGroupServiceImpl implements ShareGroupService{


    private final UserJpaRepository userJpaRepository;
    private final ShareGroupJpaRepository shareGroupJpaRepository;

    @Override
    @Transactional
    public ShareGroup createShareGroup(User user, String shareGroupName, String ImagePath) {

        ShareGroup shareGroup = ShareGroup.builder()
                .user(user)
                .shareGroupName(shareGroupName)
                .shareGroupImagePath(ImagePath).build();
         shareGroupJpaRepository.save(shareGroup);

        return shareGroup;
    }

    @Override
    public ShareGroup getShareGroupV1(Long shareGroupId) {
        Optional<ShareGroup> shareGroup = shareGroupJpaRepository.findById(shareGroupId);
        return shareGroup.get();
    }

    @Override
    public List<ShareGroup> getShareGroup(Long userId) {
        Optional<User> user = userJpaRepository.findById(userId);
        Optional<List<ShareGroup>> shareGroupList = shareGroupJpaRepository.findListAll(user.get());

        return shareGroupList.get();
    }

    @Override
    @Transactional
    public ShareGroup updateShareGroup(PatchRequest request, String imagePath) {
        Optional<ShareGroup> oShareGroup = shareGroupJpaRepository.findById(request.getShareGroupId());


        if(oShareGroup.isPresent()){
            ShareGroup shareGroup = oShareGroup.get();

           User user = new User();

            if(StringUtils.isNotBlank(request.getUserId().toString()))
                user = userJpaRepository.findById(request.getUserId()).get();
                shareGroup.updateAdmin(user);

            if(StringUtils.isNotBlank(request.getShareGroupName()))
                shareGroup.updateShareGroupName(request.getShareGroupName());

            if(StringUtils.isNotBlank(imagePath))
                shareGroup.updateShareImagePath(imagePath);

                shareGroupJpaRepository.save(shareGroup);
                return shareGroup;
        }

        return oShareGroup.get();
    }

    @Override
    public Boolean deleteShareGroup(Long shareGroupId, User user) {
        Optional<ShareGroup> shareGroup = shareGroupJpaRepository.findById(shareGroupId);
        if (shareGroup.get().getUser().equals(user)){
            shareGroupJpaRepository.delete(shareGroup.get());
            return true;
        }
        return false;
    }
}
