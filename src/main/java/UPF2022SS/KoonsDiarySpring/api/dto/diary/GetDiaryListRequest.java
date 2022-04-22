package UPF2022SS.KoonsDiarySpring.api.dto.diary;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetDiaryListRequest {
    private String accessToken;
    private Long userId;
}
