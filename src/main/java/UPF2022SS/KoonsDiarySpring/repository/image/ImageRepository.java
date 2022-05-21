package UPF2022SS.KoonsDiarySpring.repository.image;

import UPF2022SS.KoonsDiarySpring.domain.ImagePath;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository {


    Optional<ImagePath> findByPath(String path);

}
