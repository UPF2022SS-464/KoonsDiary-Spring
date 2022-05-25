package UPF2022SS.KoonsDiarySpring.api.controller;


import UPF2022SS.KoonsDiarySpring.domain.ImagePath;
import UPF2022SS.KoonsDiarySpring.service.image.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ImageApiController {


    private final ImageService imageService;

    @GetMapping(value = "/images")
    public ResponseEntity<Object> getImagePath(){
        List<ImagePath> imagePathList = imageService.findImageList().get();
        return ResponseEntity.ok().body(imagePathList);
    }
}
