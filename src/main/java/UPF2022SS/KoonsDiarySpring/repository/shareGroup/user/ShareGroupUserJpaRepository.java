package UPF2022SS.KoonsDiarySpring.repository.shareGroup.user;

import UPF2022SS.KoonsDiarySpring.domain.ShareGroupUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShareGroupUserJpaRepository extends JpaRepository<ShareGroupUser, Long>, ShareGroupUserRepository{
}
