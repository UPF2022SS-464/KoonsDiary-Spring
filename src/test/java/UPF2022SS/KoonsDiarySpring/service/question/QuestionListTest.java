package UPF2022SS.KoonsDiarySpring.service.question;

import UPF2022SS.KoonsDiarySpring.domain.Question;
import UPF2022SS.KoonsDiarySpring.repository.question.QuestionJpaRepository;
import org.aspectj.lang.annotation.Before;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest()
@ExtendWith(SpringExtension.class)
class QuestionListTest {

    @Autowired
    QuestionJpaRepository questionJpaRepository;
    @Autowired
    QuestionService questionService;

    @BeforeEach
    void createQuestion(){
        for(int i = 1; i< 6; i++){
            String content = "질문 내용 " + Integer.toString(i);
            Question question = Question.builder().question(content).build();
            questionJpaRepository.save(question);
        }
    }

    @Test
    @DisplayName("모든 질문 조회")
    void findAll() {
        //when
        List<Question> questionList = questionService.findAll();

        //then
        Assertions.assertThat(questionList).isNotEmpty();

        for (Question question : questionList) {
            System.out.println("question = " + question);
        }
    }

}