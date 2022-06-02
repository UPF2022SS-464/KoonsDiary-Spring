package UPF2022SS.KoonsDiarySpring.repository.shareGroup;

import UPF2022SS.KoonsDiarySpring.domain.ShareGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShareGroupJpaRepository extends JpaRepository<ShareGroup, String>, ShareGroupRepository{
}
