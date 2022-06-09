package UPF2022SS.KoonsDiarySpring.repository.shareGroup.user;

import UPF2022SS.KoonsDiarySpring.domain.ShareGroup;
import UPF2022SS.KoonsDiarySpring.domain.ShareGroupUser;
import UPF2022SS.KoonsDiarySpring.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShareGroupUserRepository {
    public Optional<List<ShareGroup>> findByUser(User user);
    public Optional<List<ShareGroupUser>> findAllByShareGroup(ShareGroup shareGroup);
    public Optional<ShareGroupUser> findShareGroupUserByUserAndShareGroup(User user, ShareGroup shareGroup);
}
