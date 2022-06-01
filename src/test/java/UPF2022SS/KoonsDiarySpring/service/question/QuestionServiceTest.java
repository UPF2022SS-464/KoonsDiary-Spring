package UPF2022SS.KoonsDiarySpring.service.question;

import UPF2022SS.KoonsDiarySpring.domain.ImagePath;
import UPF2022SS.KoonsDiarySpring.domain.Question;
import UPF2022SS.KoonsDiarySpring.domain.User;
import UPF2022SS.KoonsDiarySpring.repository.image.ImageJpaRepository;
import UPF2022SS.KoonsDiarySpring.repository.question.QuestionJpaRepository;
import UPF2022SS.KoonsDiarySpring.repository.user.UserJpaRepository;
import UPF2022SS.KoonsDiarySpring.service.image.ImageService;
import UPF2022SS.KoonsDiarySpring.service.user.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@SpringBootTest()
@ExtendWith(SpringExtension.class)
class QuestionServiceTest {

    @Autowired
    private QuestionJpaRepository questionJpaRepository;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private ImageService imageService;

    @Autowired
    private ImageJpaRepository imageJpaRepository;
    @Autowired
    private UserJpaRepository userJpaRepository;

    @BeforeEach
    void createQuestion(){
        for(int i = 1; i< 6; i++){
            String content = "질문 내용 " + Integer.toString(i);
            Question question = Question.builder().question(content).build();
            questionJpaRepository.save(question);
        }
    }

    @BeforeEach
    void createImage(){
        ImagePath imagePath = ImagePath.builder()
                .path("testImagePath1")
                .build();
        imageService.createImage(imagePath);
    }

    @Test
    @DisplayName("모든 질문 조회[성공]")
    void findAll() {
        //when
        List<Question> questionList = questionService.findAll();

        //then
        Assertions.assertThat(questionList).isNotEmpty();

        for (Question question : questionList) {
            System.out.println("question = " + question);
        }
    }

//    @Test
//    @DisplayName("질문에 대한 대답 작성[성공]")
//    void postQuestionAnswer(){
//        Optional<ImagePath> imagePath = imageJpaRepository.findByPath("testImagePath1");
//        User user = User.builder()
//                .username("test")
//                .email("test@gmail.com")
//                .userPwd("cucumber52")
//                .imagePath(imagePath.get())
//                .build();
//
//        userJpaRepository.save(user);
//
//        String Content = "테스트 값입니다.";
//    }


}