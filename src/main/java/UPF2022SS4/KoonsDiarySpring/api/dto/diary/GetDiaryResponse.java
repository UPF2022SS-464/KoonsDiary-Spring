package UPF2022SS4.KoonsDiarySpring.api.dto.diary;

import UPF2022SS4.KoonsDiarySpring.domain.DiaryImage;
import UPF2022SS4.KoonsDiarySpring.domain.User;
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

    private User user;

    private LocalDate writeDate;

    private LocalDateTime editionDate;

    private String content;

    private int emotion;

    private List<DiaryImage> diaryImageList;
}
