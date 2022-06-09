package UPF2022SS.KoonsDiarySpring.repository.shareGroup.user;

import UPF2022SS.KoonsDiarySpring.domain.ShareGroupUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShareGroupUserJpaRepository extends JpaRepository<ShareGroupUser, Long>, ShareGroupUserRepository{

}
