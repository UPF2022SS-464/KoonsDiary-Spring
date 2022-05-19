package UPF2022SS.KoonsDiarySpring.repository.questionAnswer;

import UPF2022SS.KoonsDiarySpring.domain.QQuestionAnswer;
import UPF2022SS.KoonsDiarySpring.domain.Question;
import UPF2022SS.KoonsDiarySpring.domain.QuestionAnswer;
import UPF2022SS.KoonsDiarySpring.domain.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class QuestionAnswerRepositoryImpl implements QuestionAnswerRepository {

    private final EntityManager em;
    private final JPAQueryFactory jqf;

    QQuestionAnswer questionAnswer = QQuestionAnswer.questionAnswer;

    @Override
    public Optional<QuestionAnswer> findByUserAndQuestion(User user, Question question) {
        return Optional.ofNullable(jqf.select(questionAnswer)
                .from(questionAnswer)
                .where(questionAnswer.user.eq(user),
                        questionAnswer.question.eq(question))
                .fetchOne());
    }
}
