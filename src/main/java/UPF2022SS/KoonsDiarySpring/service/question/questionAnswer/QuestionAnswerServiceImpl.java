package UPF2022SS.KoonsDiarySpring.service.question.questionAnswer;

import UPF2022SS.KoonsDiarySpring.api.dto.question.QuestionRequest;
import UPF2022SS.KoonsDiarySpring.domain.Question;
import UPF2022SS.KoonsDiarySpring.domain.QuestionAnswer;
import UPF2022SS.KoonsDiarySpring.domain.User;
import UPF2022SS.KoonsDiarySpring.repository.question.QuestionJpaRepository;
import UPF2022SS.KoonsDiarySpring.repository.questionAnswer.QuestionAnswerJpaRepository;
import UPF2022SS.KoonsDiarySpring.repository.user.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuestionAnswerServiceImpl implements QuestionAnswerService{

    private final UserJpaRepository userJpaRepository;
    private final QuestionJpaRepository questionJpaRepository;
    private final QuestionAnswerJpaRepository questionAnswerJpaRepository;

    @Override
    public Optional<QuestionAnswer> saveQuestionAnswer(QuestionRequest.answerRequest answerRequest){
        Optional<User> user = userJpaRepository.findById(answerRequest.getUserId());
        Optional<Question> question = questionJpaRepository.findById(answerRequest.getQuestionId());

        QuestionAnswer questionAnswer = QuestionAnswer
                .builder()
                .user(user.get())
                .question(question.get())
                .Content(answerRequest.getContent())
                .build();

        questionAnswerJpaRepository.save(questionAnswer);

        //질문과 사용자가 일치하는 질문 답변 값을 가져올 수 있도록 하자.
        return questionAnswerJpaRepository.findByUserAndQuestion(user.get(), question.get());
    }

    @Override
    public Optional<QuestionAnswer> getQuestionAnswer(User user, Question question) {
        return questionAnswerJpaRepository.findByUserAndQuestion(user, question);
    }
}
