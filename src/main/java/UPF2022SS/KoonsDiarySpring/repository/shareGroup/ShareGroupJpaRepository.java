package UPF2022SS.KoonsDiarySpring.repository.shareGroup;

import UPF2022SS.KoonsDiarySpring.domain.ShareGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShareGroupJpaRepository extends JpaRepository<ShareGroup, Long>, ShareGroupRepository{
    public Optional<ShareGroup> findById(Long id);
}
