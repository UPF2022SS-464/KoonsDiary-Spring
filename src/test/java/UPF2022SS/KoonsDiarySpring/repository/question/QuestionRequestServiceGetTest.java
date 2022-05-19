package UPF2022SS.KoonsDiarySpring.repository.question;

import UPF2022SS.KoonsDiarySpring.QuerydslConfig;
import UPF2022SS.KoonsDiarySpring.domain.Question;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@Import(QuerydslConfig.class)
@ExtendWith(SpringExtension.class)
class QuestionRequestServiceGetTest {

    @Autowired
    private QuestionJpaRepository questionJpaRepository;

    @Test
    void question_save_success() throws Exception{
        // given
        Question question = Question.builder()
                .question("당신의 오늘의 하루는 어땠나요?")
                .build();

        // when
        questionJpaRepository.save(question);
        Optional<Question> findQuestion = questionJpaRepository.findByQuestion("당신의 오늘의 하루는 어땠나요?");

        // then
        assertThat(findQuestion.get()).isEqualTo(question);
        System.out.println("findQuestion = " + findQuestion.get().getId() + " " + findQuestion.get().getQuestion());
    }
}