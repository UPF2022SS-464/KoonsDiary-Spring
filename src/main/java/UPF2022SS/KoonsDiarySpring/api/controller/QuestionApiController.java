package UPF2022SS.KoonsDiarySpring.api.controller;

import UPF2022SS.KoonsDiarySpring.domain.Question;
import UPF2022SS.KoonsDiarySpring.service.JwtService;
import UPF2022SS.KoonsDiarySpring.service.question.QuestionService;
import UPF2022SS.KoonsDiarySpring.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class QuestionApiController {

    private final UserService userService;
    private final JwtService jwtService;
    private final QuestionService questionService;
    @GetMapping(value = "/question")
    public ResponseEntity<List<Question>> getQuestionList(@RequestHeader final String header){
        //exceptionhanlder를 통해 null 예외처리 수행
        Long userId = jwtService.decodeAccessToken(header);

        List<Question> questionList = questionService.findAll();
        return ResponseEntity
                .ok()
                .body(questionList);
    }
}
