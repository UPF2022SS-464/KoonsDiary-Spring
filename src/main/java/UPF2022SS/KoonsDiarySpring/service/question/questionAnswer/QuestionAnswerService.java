package UPF2022SS.KoonsDiarySpring.service.question.questionAnswer;

import UPF2022SS.KoonsDiarySpring.domain.Question;
import UPF2022SS.KoonsDiarySpring.domain.QuestionAnswer;
import UPF2022SS.KoonsDiarySpring.domain.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static UPF2022SS.KoonsDiarySpring.api.dto.question.Question.*;

@Service
public interface QuestionAnswerService {
    public Optional<QuestionAnswer> saveQuestionAnswer(User user, AnswerRequest answerRequest);

    public Optional<QuestionAnswer> getQuestionAnswer(User user, Question question);
//    Optional<QuestionAnswer>
}
