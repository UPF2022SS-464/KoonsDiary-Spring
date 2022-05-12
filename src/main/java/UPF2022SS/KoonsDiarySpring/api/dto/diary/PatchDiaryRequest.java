package UPF2022SS.KoonsDiarySpring.api.dto.diary;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatchDiaryRequest {

    private Long id;

    private String content;

    private LocalDateTime editionDate;

    private List<String> comment;

}
