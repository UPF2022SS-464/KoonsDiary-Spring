package UPF2022SS.KoonsDiarySpring.api.dto.diary;

import UPF2022SS.KoonsDiarySpring.domain.DiaryImage;
import UPF2022SS.KoonsDiarySpring.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder @Getter
@AllArgsConstructor
public class GetDiaryResponse {

    private Long id;

    private LocalDate writeDate;

    private LocalDateTime editionDate;

    private String content;

    private int emotion;

    private List<DiaryImage> diaryImageList;
}
