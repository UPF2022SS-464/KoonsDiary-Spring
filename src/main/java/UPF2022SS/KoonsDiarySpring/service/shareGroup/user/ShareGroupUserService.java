package UPF2022SS.KoonsDiarySpring.service.shareGroup.user;

import UPF2022SS.KoonsDiarySpring.domain.ShareGroupInvite;
import UPF2022SS.KoonsDiarySpring.domain.ShareGroupUser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ShareGroupUserService {

    public ShareGroupUser createShareGroupUser(Long shareGroupInviteId);
    public List<ShareGroupUser> getShareGroupUsers(Long shareGroupid);
    public ShareGroupUser getShareGroupUser(Long userId, Long ShareGroupId);
    public void expelShareGroupUser(Long id);


}
