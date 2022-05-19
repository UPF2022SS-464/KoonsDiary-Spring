package UPF2022SS.KoonsDiarySpring.api.dto.question;

import lombok.Data;

import javax.validation.constraints.NotBlank;

public class QuestionRequest {

    @Data
    public static class answerRequest{
        private Long questionId;

        private Long userId;

        private String content;
    }

}
