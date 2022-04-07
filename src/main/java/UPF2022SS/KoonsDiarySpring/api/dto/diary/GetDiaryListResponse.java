package UPF2022SS.KoonsDiarySpring.api.dto.diary;

import UPF2022SS.KoonsDiarySpring.domain.Diary;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Getter @Builder
@AllArgsConstructor
public class GetDiaryListResponse {
    private List<Diary>  diaryList;
}
