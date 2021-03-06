package UPF2022SS.KoonsDiarySpring.repository.image;

import UPF2022SS.KoonsDiarySpring.domain.ImagePath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageJpaRepository extends JpaRepository<ImagePath,Long>, ImageRepository{
    Optional<ImagePath> findByPath(String path);
}
