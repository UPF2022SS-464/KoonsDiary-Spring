package UPF2022SS.KoonsDiarySpring.repository.shareGroup.invite;

import UPF2022SS.KoonsDiarySpring.domain.ShareGroupInvite;
import UPF2022SS.KoonsDiarySpring.domain.User;

import java.util.List;
import java.util.Optional;

public interface ShareGroupInviteRepository {

    public Optional<List<ShareGroupInvite>> findUserByShareGroupId(Long shareGroupId);

    public Optional<List<ShareGroupInvite>> findWaitByUserId(User user);

    public Optional<List<ShareGroupInvite>> findWaitUserByShareGroupId(Long shareGroupId);
}
