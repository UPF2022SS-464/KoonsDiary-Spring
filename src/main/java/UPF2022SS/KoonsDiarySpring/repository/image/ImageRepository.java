package UPF2022SS.KoonsDiarySpring.repository.image;

import UPF2022SS.KoonsDiarySpring.domain.Image;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepository {

    Optional<List<Image>> findAll();

    Optional<Image> findByPath(String path);

}
