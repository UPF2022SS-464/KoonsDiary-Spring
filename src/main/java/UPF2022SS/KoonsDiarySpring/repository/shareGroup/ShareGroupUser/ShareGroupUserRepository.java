package UPF2022SS.KoonsDiarySpring.repository.shareGroup.ShareGroupUser;

import UPF2022SS.KoonsDiarySpring.domain.ShareGroup;
import UPF2022SS.KoonsDiarySpring.domain.ShareGroupUser;
import UPF2022SS.KoonsDiarySpring.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShareGroupUserRepository {
    Optional<List<ShareGroup>> findByUser(User user);
}
