package UPF2022SS.KoonsDiarySpring.service.image;

import UPF2022SS.KoonsDiarySpring.domain.Image;
import UPF2022SS.KoonsDiarySpring.repository.image.ImageJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor()
public class ImageServiceImpl implements ImageService{

    private final ImageJpaRepository imageJpaRepository;

    @Override
    public Optional<List<Image>>findImageList() {
        return imageJpaRepository.findAll();
    }

    @Override
    public void createImage(Image image) {
        imageJpaRepository.save(image);
    }

    @Override
    public Optional<Image> findImage(Long id) {
        return imageJpaRepository.findById(id);
    }
}
