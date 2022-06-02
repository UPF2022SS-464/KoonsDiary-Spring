package UPF2022SS.KoonsDiarySpring.service.shareGroup;

import UPF2022SS.KoonsDiarySpring.domain.ShareGroup;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ShareGroupService {
    public ShareGroup createShareGroup(Long userId, String shareGroupName, String ImagePath);
    public List<ShareGroup> getShareGroup(Long userId);
    public ShareGroup updateShareGroup(Long userId, String shareGroupName, String ImagePath);
    public void deleteShareGroup(Long shareGroupId);
}
