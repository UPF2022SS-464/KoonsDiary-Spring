package UPF2022SS.KoonsDiarySpring.api.dto.diary;

import UPF2022SS.KoonsDiarySpring.domain.DiaryImage;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDiaryRequest {

    private LocalDate writeDate;

    private LocalDateTime editionDate;

    private String content;

    private int emotion;

    private List<String> comment;

    private String thumbnailPath;
}
