package UPF2022SS.KoonsDiarySpring.api.dto.question;

import lombok.Data;

public class Question{

    @Data
    public static class AnswerRequest {
        private Long questionId;

        private String content;
    }

    @Data
    public static class EmpathyRequest{
        private Long questionAnswerId;

        private String emotion;
    }

}
