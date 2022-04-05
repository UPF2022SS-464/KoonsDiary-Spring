package UPF2022SS4.KoonsDiarySpring.api.dto.diary;

import UPF2022SS4.KoonsDiarySpring.domain.DiaryImage;
import UPF2022SS4.KoonsDiarySpring.domain.User;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDiaryRequest {

    private User user;

    private LocalDate writeDate;

    private LocalDateTime editionDate;

    private String content;

    private int emotion;

    private List<DiaryImage> diaryImageList;

    private String thumbnailPath;
}
