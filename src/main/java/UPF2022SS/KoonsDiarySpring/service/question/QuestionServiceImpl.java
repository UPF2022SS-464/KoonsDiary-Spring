package UPF2022SS.KoonsDiarySpring.service.question;

import UPF2022SS.KoonsDiarySpring.domain.Question;
import UPF2022SS.KoonsDiarySpring.repository.question.QuestionJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor()
public class QuestionServiceImpl implements QuestionService{

    private final QuestionJpaRepository questionJpaRepository;

    @Override
    public Optional<Question> findByQuestionId(Long id) {
        Optional<Question> question = questionJpaRepository.findById(id);
        return question;
    }

    @Override
    public Optional<Question> findByQuestionContent(String content) {
        Optional<Question> question = questionJpaRepository.findByQuestion(content);
        return question;
    }
}
