package UPF2022SS.KoonsDiarySpring.service.shareGroup;

import UPF2022SS.KoonsDiarySpring.domain.ShareGroup;
import UPF2022SS.KoonsDiarySpring.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;

import static UPF2022SS.KoonsDiarySpring.api.dto.shareGroup.ShareGroup.*;

@Service
public interface ShareGroupService {
    public ShareGroup createShareGroup(User user, String shareGroupName, String ImagePath);
    public List<ShareGroup> getShareGroup(Long userId);
    public ShareGroup updateShareGroup(PatchRequest request, String imagePath);
    public Boolean deleteShareGroup(Long shareGroupId, User uer);
}
