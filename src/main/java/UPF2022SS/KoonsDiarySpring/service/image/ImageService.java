package UPF2022SS.KoonsDiarySpring.service.image;

import UPF2022SS.KoonsDiarySpring.domain.ImagePath;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ImageService {

    public Optional<List<ImagePath>> findImageList();
    public void createImage(ImagePath imagePath);
    public Optional<ImagePath> findImage(Long id);
}
