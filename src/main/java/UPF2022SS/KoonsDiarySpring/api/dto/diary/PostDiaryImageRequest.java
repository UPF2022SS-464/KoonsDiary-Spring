package UPF2022SS.KoonsDiarySpring.api.dto.diary;
import UPF2022SS.KoonsDiarySpring.domain.Diary;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import static javax.persistence.FetchType.LAZY;

@Data
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDiaryImageRequest {
    private String image_path;
    private String comment;
}
