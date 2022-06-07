package UPF2022SS.KoonsDiarySpring.service.shareGroup.Invite;

import UPF2022SS.KoonsDiarySpring.api.dto.shareGroup.invite.ShareGroupInvite.*;

import UPF2022SS.KoonsDiarySpring.domain.ShareGroupInvite;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ShareGroupInviteService {
    public String createShareGroupInvites(createRequest request);
    public List<ShareGroupInvite> getShareGroupInvite(getRequest request);
    public List<ShareGroupInvite> DeleteShareGroupInvite(deleteRequest request);
}
