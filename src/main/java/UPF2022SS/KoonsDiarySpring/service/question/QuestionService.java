package UPF2022SS.KoonsDiarySpring.service.question;


import UPF2022SS.KoonsDiarySpring.domain.Question;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface QuestionService {
    public Optional<Question> findByQuestionId(Long id);
    public Optional<Question> findByQuestionContent(String content);

}
