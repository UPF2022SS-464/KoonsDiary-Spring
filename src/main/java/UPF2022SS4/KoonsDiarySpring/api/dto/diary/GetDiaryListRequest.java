package UPF2022SS4.KoonsDiarySpring.api.dto.diary;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetDiaryListRequest {

    private Long userId;
}
