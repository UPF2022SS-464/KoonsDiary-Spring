package UPF2022SS.KoonsDiarySpring.service.image;

import UPF2022SS.KoonsDiarySpring.domain.Diary;
import UPF2022SS.KoonsDiarySpring.domain.Image;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ImageService {

    public Optional<List<Image>> findImageList();
    public void createImage(Image image);
    public Optional<Image> findImage(Long id);
}
