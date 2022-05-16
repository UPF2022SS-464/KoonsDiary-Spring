package UPF2022SS.KoonsDiarySpring.api.dto.diary;

import UPF2022SS.KoonsDiarySpring.domain.DiaryImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class GetDiary {
    @Data
    public static class Request{
        private Long id;
    }

    @Data
    @Builder
    @AllArgsConstructor
    public static class Response{
        private Long id;
        private LocalDate writeDate;
        private LocalDateTime editionDate;
        private String content;
        private int emotion;
//        private Optional<List<DiaryImage>> diaryImageList;
        private List<String> imagePaths;
        private List<String> comments;
    }

    @Data
    @Builder
    @AllArgsConstructor
    public static class MonthlyResponse{
        private Long id;
        private LocalDate writeDate;
        private LocalDateTime editionDate;
        private String content;
        private int emotion;
        //        private Optional<List<DiaryImage>> diaryImageList;
        private List<String> imagePaths;
        private List<String> comments;
    }

}
