package UPF2022SS.KoonsDiarySpring.service.shareGroup.user;

import UPF2022SS.KoonsDiarySpring.domain.Enum.Authority;
import UPF2022SS.KoonsDiarySpring.domain.Enum.InvitationStatus;
import UPF2022SS.KoonsDiarySpring.domain.ShareGroup;
import UPF2022SS.KoonsDiarySpring.domain.ShareGroupInvite;
import UPF2022SS.KoonsDiarySpring.domain.ShareGroupUser;
import UPF2022SS.KoonsDiarySpring.domain.User;
import UPF2022SS.KoonsDiarySpring.repository.shareGroup.ShareGroupJpaRepository;
import UPF2022SS.KoonsDiarySpring.repository.shareGroup.invite.ShareGroupInviteJpaRepository;
import UPF2022SS.KoonsDiarySpring.repository.shareGroup.user.ShareGroupUserJpaRepository;
import UPF2022SS.KoonsDiarySpring.repository.user.UserJpaRepository;
import UPF2022SS.KoonsDiarySpring.service.shareGroup.firebase.FirebaseCloudMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ShareGroupUserServiceImpl implements ShareGroupUserService{

    private final UserJpaRepository userJpaRepository;
    private final ShareGroupJpaRepository shareGroupJpaRepository;
    private final ShareGroupUserJpaRepository shareGroupUserJpaRepository;
    private final ShareGroupInviteJpaRepository shareGroupInviteJpaRepository;
    private final FirebaseCloudMessageService firebaseCloudMessageService;

    /*
    *  공유일기 유저 생성기
    */
    @Override
    @Transactional
    public ShareGroupUser createShareGroupUser(Long ShareGroupInviteId) {

        ShareGroupInvite shareGroupInvite = shareGroupInviteJpaRepository.findById(ShareGroupInviteId).get();

        shareGroupInvite.setStatus(InvitationStatus.ACCEPT);
        shareGroupInviteJpaRepository.save(shareGroupInvite);

        ShareGroupUser shareGroupUser = ShareGroupUser.builder()
                .user(shareGroupInvite.getUser())
                .shareGroup(shareGroupInvite.getShareGroup())
                .authority(Authority.MEMBER)
                .build();

        shareGroupUserJpaRepository.save(shareGroupUser);
        return shareGroupUser;
    }

    @Override
    public List<ShareGroupUser> getShareGroupUsers(Long shareGroupId) {

        Optional<ShareGroup> shareGroup = shareGroupJpaRepository.findById(shareGroupId);

        return shareGroupUserJpaRepository.findAllByShareGroup(shareGroup.get()).get();
    }


    /*
    * 유저 정보와 공유일기 정보를 통해 공유일기장유저의 정보를 가져온다.
    */
    @Override
    public ShareGroupUser getShareGroupUser(Long userId, Long shareGroupId)
    {
        Optional<User> user = userJpaRepository.findById(userId);
        Optional<ShareGroup> shareGroup = shareGroupJpaRepository.findById(shareGroupId);

        Optional<ShareGroupUser> shareGroupUser = shareGroupUserJpaRepository.findShareGroupUserByUserAndShareGroup(user.get(), shareGroup.get());

        return shareGroupUser.get();
    }

    /*
    * 공유일기장에서 유저 추방하기
    * */
    @Override
    @Transactional
    public void expelShareGroupUser(Long id) {
        Optional<ShareGroupUser> shareGroupUser = shareGroupUserJpaRepository.findById(id);

        String shareGroupName = shareGroupUser.get().getShareGroup().getShareGroupName();
        String title = "공유 일기 알림";
        String body = shareGroupName+"에서 퇴장당하셨습니다.";

        try {
            firebaseCloudMessageService.sendMessageTo(shareGroupUser.get().getUser().getFcmToken(), title, body);
            shareGroupUserJpaRepository.delete(shareGroupUser.get());
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }
}
