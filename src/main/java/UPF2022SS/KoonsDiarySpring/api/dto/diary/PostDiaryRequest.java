package UPF2022SS.KoonsDiarySpring.api.dto.diary;

import UPF2022SS.KoonsDiarySpring.domain.DiaryImage;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDiaryRequest {

    //유저에 대한 정보는 Header의 토큰으로 받아옵니다.

    private LocalDate writeDate;

    private LocalDateTime editionDate;

    private String content;

    private List<String> comment;
}
