package UPF2022SS.KoonsDiarySpring.api.controller;

import UPF2022SS.KoonsDiarySpring.api.dto.question.Question.AnswerRequest;
import UPF2022SS.KoonsDiarySpring.domain.Question;
import UPF2022SS.KoonsDiarySpring.domain.QuestionAnswer;
import UPF2022SS.KoonsDiarySpring.domain.User;
import UPF2022SS.KoonsDiarySpring.service.JwtService;
import UPF2022SS.KoonsDiarySpring.service.question.QuestionService;
import UPF2022SS.KoonsDiarySpring.service.question.questionAnswer.QuestionAnswerService;
import UPF2022SS.KoonsDiarySpring.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
public class QuestionApiController {

    private final UserService userService;
    private final JwtService jwtService;
    private final QuestionService questionService;
    private final QuestionAnswerService questionAnswerService;

    @GetMapping(value = "/question")
    public ResponseEntity<List<Question>> getQuestionList(@RequestHeader final String header){
        //exceptionhanlder를 통해 null 예외처리 수행
        Long userId = jwtService.decodeAccessToken(header);

        List<Question> questionList = questionService.findAll();
        return ResponseEntity
                .ok()
                .body(questionList);
    }

    @PostMapping(value = "/question")
    public ResponseEntity<QuestionAnswer> postQuestionAnswer(
            @RequestHeader final String header,
            @RequestBody final AnswerRequest request){

        User user = userService.findById(jwtService.decodeAccessToken(header));
        Optional<QuestionAnswer> questionAnswer = questionAnswerService.saveQuestionAnswer(user, request);

        ResponseEntity<QuestionAnswer> body = ResponseEntity
                .ok()
                .body(questionAnswer.get());
        return body;
    }

    @PostMapping(value = "/question/{id}")
    public ResponseEntity<QuestionAnswer> getQuestionAnswer(
            @RequestHeader final String header,
            @PathVariable("id") final Long id
    ){
        User user = userService.findById(jwtService.decodeAccessToken(header));
        Question question = questionService.findByQuestionId(id).get();
        Optional<QuestionAnswer> questionAnswer = questionAnswerService.getQuestionAnswer(user, question);

        return ResponseEntity
                .ok()
                .body(questionAnswer.get());
    }

//    @PostMapping(value = "question/empathy")
//    public ResponseEntity<String> empathizeQuestionAnswer(
//
//    )
}
