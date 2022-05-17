package UPF2022SS.KoonsDiarySpring.repository.question;

import UPF2022SS.KoonsDiarySpring.domain.QQuestion;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Slf4j
@Repository
@RequiredArgsConstructor
public class QuestionRepositoryImpl implements QuestionRepository{
    private final JPAQueryFactory jqf;
    private EntityManager em;
    QQuestion question = QQuestion.question1;

}
