package UPF2022SS4.KoonsDiarySpring.api.controller;

import UPF2022SS4.KoonsDiarySpring.api.dto.DefaultResponse;
import UPF2022SS4.KoonsDiarySpring.api.dto.user.LoginRequest;
import UPF2022SS4.KoonsDiarySpring.api.dto.user.SignUpRequest;
import UPF2022SS4.KoonsDiarySpring.common.ResponseMessage;
import UPF2022SS4.KoonsDiarySpring.common.StatusCode;
import UPF2022SS4.KoonsDiarySpring.domain.User;
import UPF2022SS4.KoonsDiarySpring.service.AuthService;
import UPF2022SS4.KoonsDiarySpring.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserApiController {


    @Autowired
    private AuthService authService;
    @Autowired
    private UserService userService;

    @PostMapping(value = "/signup")
    public DefaultResponse signUp(@RequestBody final SignUpRequest signUpRequest){
        try{
            User findUser = userService.findUserEmail(signUpRequest.getEmail());

            if (findUser != null){
                return DefaultResponse.response(StatusCode.UNAUTHORIZED, ResponseMessage.DUPLICATED_USER);
            }
            User user = User.builder()
                    .username(signUpRequest.getUserId())
                    .password(signUpRequest.getPassword())
                    .email(signUpRequest.getEmail())
                    .nickname(signUpRequest.getNickname())
                    .build();
            userService.join(user);

            DefaultResponse response = authService.signUpLogin(signUpRequest);

            return response;

        } catch (Exception e){
            log.error(e.getMessage());
        }
        return DefaultResponse.response(StatusCode.INTERNAL_SERVER_ERROR, ResponseMessage.NOT_CONTENT);
    }

    @PostMapping(value = "/login")
    public DefaultResponse login(@RequestBody final LoginRequest loginRequest){
        try{
            User findUser;
            //아이디 형식이 이메일일 경우
            if (loginRequest.getUserId().contains("@")){
                findUser = userService.findUserEmail(loginRequest.getUserId());
            }
            //일반 아이디 형식일 경우
            else{
               findUser = userService.findUsername(loginRequest.getUserId());
            }

            //찾아낸 사용자가 없을 경우
            if(findUser == null){
                return DefaultResponse.response(
                        StatusCode.UNAUTHORIZED,
                        ResponseMessage.INVALID_USER);
            }

            DefaultResponse response = authService.login(loginRequest);

            return response;
        }
        catch (Exception e){
            log.error(e.getMessage());
        }
        return DefaultResponse.response(StatusCode.INTERNAL_SERVER_ERROR, ResponseMessage.NOT_CONTENT);
    }
}
