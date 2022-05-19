package UPF2022SS.KoonsDiarySpring.repository.questionAnswer;

import UPF2022SS.KoonsDiarySpring.domain.Question;
import UPF2022SS.KoonsDiarySpring.domain.QuestionAnswer;
import UPF2022SS.KoonsDiarySpring.domain.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuestionAnswerRepository {

    Optional<QuestionAnswer> findByUserAndQuestion(User user, Question question);
}
