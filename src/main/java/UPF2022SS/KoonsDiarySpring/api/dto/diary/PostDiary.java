package UPF2022SS.KoonsDiarySpring.api.dto.diary;

import UPF2022SS.KoonsDiarySpring.domain.User;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PostDiary {

    // 다이어리 포스팅 DTO
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        //유저에 대한 정보는 Header의 토큰으로 받아옵니다.
//        private LocalDate writeDate;
//        private LocalDateTime editionDate;
        private String content;
        private List<String> comment;
        private List<MultipartFile> files = new ArrayList<>();
    }

    @Data
    @AllArgsConstructor
    public static class Response {
        private Long id;
        private Long user;
        private LocalDate writeTime;
        private LocalDateTime editionDate;
        private String content;
        private int emotion;
    }
}
