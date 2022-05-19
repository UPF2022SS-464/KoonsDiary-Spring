package UPF2022SS.KoonsDiarySpring.repository.questionAnswer;

import UPF2022SS.KoonsDiarySpring.QuerydslConfig;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@Import(QuerydslConfig.class)
@ExtendWith(SpringExtension.class)
class QuestionAnswerGetTest {

    @Autowired
    private QuestionAnswerJpaRepository questionAnswerJpaRepository;
}