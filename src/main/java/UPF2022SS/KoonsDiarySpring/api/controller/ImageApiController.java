package UPF2022SS.KoonsDiarySpring.api.controller;


import UPF2022SS.KoonsDiarySpring.domain.ImagePath;
import UPF2022SS.KoonsDiarySpring.service.image.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ImageApiController {

    private final ImageService imageService;

    @GetMapping(value = "/images")
    public ResponseEntity<Object> getImagePath(){
        Optional<List<ImagePath>> imagePaths = imageService.findImageList();
        if (imagePaths.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(imagePaths.get());
    }
}
