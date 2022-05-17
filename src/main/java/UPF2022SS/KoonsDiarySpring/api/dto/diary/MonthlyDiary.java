package UPF2022SS.KoonsDiarySpring.api.dto.diary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class MonthlyDiary {

    private Long id;

    private LocalDate writeDate;

    private int emotion;
}