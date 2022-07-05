package UPF2022SS.KoonsDiarySpring.service.shareGroup.Invite;

import UPF2022SS.KoonsDiarySpring.api.dto.shareGroup.invite.ShareGroupInvite.*;

import UPF2022SS.KoonsDiarySpring.domain.Enum.InvitationStatus;
import UPF2022SS.KoonsDiarySpring.domain.ShareGroup;
import UPF2022SS.KoonsDiarySpring.domain.ShareGroupInvite;
import UPF2022SS.KoonsDiarySpring.domain.User;
import UPF2022SS.KoonsDiarySpring.repository.shareGroup.ShareGroupJpaRepository;
import UPF2022SS.KoonsDiarySpring.repository.shareGroup.invite.ShareGroupInviteJpaRepository;
import UPF2022SS.KoonsDiarySpring.repository.user.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ShareGroupInviteServiceImpl implements ShareGroupInviteService{

    private final ShareGroupInviteJpaRepository shareGroupInviteJpaRepository;
    private final ShareGroupJpaRepository shareGroupJpaRepository;
    private final UserJpaRepository userJpaRepository;

    /*
    입력받은 모든 사용자에 대한 초대 비즈니스 로직
    사용자에 대한 id가 담긴 dto를 받아와 유저 객체로 만든 후 그룹 아이디를 통해
    sharegroupInvite 객체를 생성
    saveAll()을 통해 저장 한 후 해당 유저에 대한 FCM_TOKEN을 통해 메시지 전달
    */

    @Override
    @Transactional
    public String createShareGroupInvites(createRequest request) {
        Optional<User> user = userJpaRepository.findById(request.getUser());
        Optional<ShareGroup> shareGroup = shareGroupJpaRepository.findById(request.getShareGroupId());

        ShareGroupInvite shareGroupInvite = ShareGroupInvite
                .builder()
                .user(user.get())
                .shareGroup(shareGroup.get())
                .build();

        shareGroupInviteJpaRepository.save(shareGroupInvite);

        return shareGroupInvite.getUser().getFcmToken();
    }

    @Override
    public List<ShareGroupInvite> getShareGroupInvite(getRequest request) {
        Optional<List<ShareGroupInvite>> users = shareGroupInviteJpaRepository
                .findWaitUserByShareGroupId(request.getShareGroupId());

        return users.get();
    }

    // 초대받은 사람의 관점
    // 초대에 대해 거절상태로 두기 위한 비즈니스 로직
    @Override
    @Transactional
    public List<ShareGroupInvite> rejectShareGroupInvite(deleteRequest request) {
        Optional<ShareGroupInvite> shareGroupInvite = shareGroupInviteJpaRepository.findById(request.getShareGroupInviteId());


        shareGroupInvite.get().setStatus(InvitationStatus.REJECT);
        Long shareGroupId = shareGroupInvite.get().getShareGroup().getId();


        Optional<List<ShareGroupInvite>> users = shareGroupInviteJpaRepository.findWaitByUserId(shareGroupInvite.get().getUser());

        List<ShareGroupInvite> shareGroupInvites = users.get();
        return shareGroupInvites;
    }

    // 초대한 사람의 관점
    // 잘못 초대했을 경우 해당 초대를 삭제하는 비즈니스 로직
    @Override
    public List<ShareGroupInvite> deleteShareGroupInvite(deleteRequest request) {
        Optional<ShareGroupInvite> shareGroupInvite = shareGroupInviteJpaRepository.findById(request.getShareGroupInviteId());

        Long shareGroupId = shareGroupInvite.get().getShareGroup().getId();

        shareGroupInviteJpaRepository.delete(shareGroupInvite.get());

        List<ShareGroupInvite> shareGroupInvites = shareGroupInviteJpaRepository.findWaitUserByShareGroupId(shareGroupId).get();
        return shareGroupInvites;
    }
}

