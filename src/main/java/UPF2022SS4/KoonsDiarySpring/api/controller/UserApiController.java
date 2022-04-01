package UPF2022SS4.KoonsDiarySpring.api.controller;

import UPF2022SS4.KoonsDiarySpring.api.dto.DefaultResponse;
import UPF2022SS4.KoonsDiarySpring.api.dto.user.LoginRequest;
import UPF2022SS4.KoonsDiarySpring.api.dto.user.SignUpRequest;
import UPF2022SS4.KoonsDiarySpring.common.ResponseMessage;
import UPF2022SS4.KoonsDiarySpring.common.StatusCode;
import UPF2022SS4.KoonsDiarySpring.domain.RefreshToken;
import UPF2022SS4.KoonsDiarySpring.domain.User;
import UPF2022SS4.KoonsDiarySpring.service.AuthService;
import UPF2022SS4.KoonsDiarySpring.service.RefreshTokenService;
import UPF2022SS4.KoonsDiarySpring.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.web.bind.annotation.*;

import java.io.Console;
import java.time.LocalDate;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserApiController {


    @Autowired
    private AuthService authService;
    @Autowired
    private UserService userService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @PostMapping(value = "/signup")
    public DefaultResponse signUp(@RequestBody final SignUpRequest signUpRequest){
        try{
            User findUser = userService.findUserEmail(signUpRequest.getEmail());
            if (findUser != null){
                return DefaultResponse.response(
                        StatusCode.UNAUTHORIZED,
                        ResponseMessage.DUPLICATED_USER);
            }
            User user = User.builder()
                    .username(signUpRequest.getUserId())
                    .password(signUpRequest.getPassword())
                    .email(signUpRequest.getEmail())
                    .nickname(signUpRequest.getNickname())
                    .build();

            RefreshToken refreshToken = new RefreshToken(user, authService.createRefreshToken());

            userService.join(user);
            refreshTokenService.save(refreshToken);

            //response 반환
            return authService.signUpLogin(signUpRequest, refreshToken.getValue());

        } catch (Exception e){
            log.error(e.getMessage());
            return DefaultResponse.response(
                    StatusCode.INTERNAL_SERVER_ERROR,
                    ResponseMessage.NOT_CONTENT);
        }
    }

    @PostMapping(value = "/login")
    public DefaultResponse login(@RequestBody final LoginRequest loginRequest){
        try{
            User findUser;
            String refreshToken = loginRequest.getRefreshToken();
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

            /* 아이디 검증 이후의 부분,
            * refresh token의 만료일자를 확인 후,
            * 만료 이전일 경우 access token을 발급한다.
            * token이 만료되었을 경우 refresh token을 재발급 하고 access token을 발급한다.
            * */
            boolean checkExpirationDate =  authService.checkExpirationDate(refreshToken);

            if(!checkExpirationDate){
                refreshToken = refreshTokenService.updateToken(authService.createRefreshToken());
                return authService.login(loginRequest, refreshToken);
                }

            return authService.login(loginRequest, refreshToken);
            }
            catch (Exception e){
                log.error(e.getMessage());
                return DefaultResponse.response(
                        StatusCode.INTERNAL_SERVER_ERROR,
                        ResponseMessage.INTERNAL_SERVER_ERROR);
            }

        }

    //유저 정보 get api
    @GetMapping(value = "/user")
    public DefaultResponse getUser(@RequestHeader("Authorization") final String header){
        return null;
        }
    }

