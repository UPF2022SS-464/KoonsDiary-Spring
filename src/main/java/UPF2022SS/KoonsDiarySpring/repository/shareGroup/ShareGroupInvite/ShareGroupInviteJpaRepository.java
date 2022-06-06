package UPF2022SS.KoonsDiarySpring.repository.shareGroup.ShareGroupInvite;

import UPF2022SS.KoonsDiarySpring.domain.ShareGroupInvite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShareGroupInviteJpaRepository extends JpaRepository<ShareGroupInvite, Long>, ShareGroupInviteRepository{
}
