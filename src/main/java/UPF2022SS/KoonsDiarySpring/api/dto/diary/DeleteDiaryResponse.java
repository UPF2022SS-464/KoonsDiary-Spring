package UPF2022SS.KoonsDiarySpring.api.dto.diary;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
@Getter
@AllArgsConstructor
public class DeleteDiaryResponse {

    private Long id;

}
