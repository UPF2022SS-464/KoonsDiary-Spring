package UPF2022SS.KoonsDiarySpring.api.dto.diary;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class DeleteDiaryRequest {
    private Long id;
}
