package UPF2022SS.KoonsDiarySpring.api.dto.diary;

import UPF2022SS.KoonsDiarySpring.domain.User;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PostDiaryResponse {
    private Long id;

    private User user;

    private LocalDate writeTime;

    private LocalDateTime editionDate;

    private String content;

    private int emotion;
}
