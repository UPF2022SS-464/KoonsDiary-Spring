package UPF2022SS.KoonsDiarySpring.api.dto.diary;

import UPF2022SS.KoonsDiarySpring.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class PatchDiaryResponse {

    private Long id;

    private User user;

    private LocalDate writeTime;

    private LocalDateTime editionDate;

    private String content;

    private int emotion;
}
