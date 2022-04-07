package UPF2022SS4.KoonsDiarySpring.api.controller;

import UPF2022SS4.KoonsDiarySpring.api.dto.DefaultResponse;
import UPF2022SS4.KoonsDiarySpring.api.dto.diary.PostDiaryRequest;
import UPF2022SS4.KoonsDiarySpring.common.ResponseMessage;
import UPF2022SS4.KoonsDiarySpring.common.StatusCode;
import UPF2022SS4.KoonsDiarySpring.domain.User;
import UPF2022SS4.KoonsDiarySpring.service.diary.DiaryService;
import UPF2022SS4.KoonsDiarySpring.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class DIaryApiController {

    @Autowired
    private DiaryService diaryService;
    @Autowired
    private UserService userService;

    @PostMapping(value = "/diary")
    public DefaultResponse postDiary(@RequestBody final PostDiaryRequest postDiaryRequest){
        try{
            User findUser = userService.findById(postDiaryRequest.getUser());
            if (findUser ==null){
                return DefaultResponse.response(
                        StatusCode.UNAUTHORIZED,
                        ResponseMessage.INVALID_USER
                );
            }

            DefaultResponse response =  diaryService.postDiary(postDiaryRequest);
            return response;

        } catch (Exception e){
            log.error(e.getMessage());
            return DefaultResponse.response(
                    StatusCode.INTERNAL_SERVER_ERROR,
                    ResponseMessage.DIARY_POST_FAIL
            );
        }
    }
}
