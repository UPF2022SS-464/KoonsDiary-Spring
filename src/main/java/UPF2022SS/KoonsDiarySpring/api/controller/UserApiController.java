package UPF2022SS.KoonsDiarySpring.api.controller;

import UPF2022SS.KoonsDiarySpring.api.dto.user.*;
import UPF2022SS.KoonsDiarySpring.api.dto.DefaultResponse;
import UPF2022SS.KoonsDiarySpring.common.ResponseMessage;
import UPF2022SS.KoonsDiarySpring.common.StatusCode;
import UPF2022SS.KoonsDiarySpring.domain.RefreshToken;
import UPF2022SS.KoonsDiarySpring.domain.User;
import UPF2022SS.KoonsDiarySpring.service.AuthService;
import UPF2022SS.KoonsDiarySpring.service.JwtService;
import UPF2022SS.KoonsDiarySpring.service.RefreshTokenService;
import UPF2022SS.KoonsDiarySpring.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserApiController {


    @Autowired
    private AuthService authService;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private RefreshTokenService refreshTokenService;

    @PostMapping(value = "/user")
    public DefaultResponse saveUser(@RequestBody final SignUpRequest signUpRequest){
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
                        ResponseMessage.NOT_FOUND_USER
                    );
                }

            /* 아이디 검증 이후의 부분,
            * refresh token의 만료일자를 확인 후,
            * 만료 이전일 경우 access token을 발급한다.
            * token이 만료되었을 경우 refresh token을 재발급 하고 access token을 발급한다.
            * */
            boolean checkExpirationDate =  authService.checkExpirationDate(refreshToken);
            System.out.println(checkExpirationDate);
//            if(!checkExpirationDate){
//                refreshToken = refreshTokenService.updateToken(authService.createRefreshToken());
//                return authService.login(loginRequest, refreshToken);
//                }

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
        try{
            if (header == null){
                return DefaultResponse.response(
                        StatusCode.UNAUTHORIZED,
                        ResponseMessage.UNAUTHORIZED
                );
            }
            Long userId = jwtService.decodeAccessToken(header);
            User findUser = userService.findById(userId);

            UserInfoResponse userInfoResponse= UserInfoResponse.builder()
                    .userName(findUser.getUsername())
                    .nickName(findUser.getNickname())
                    .email(findUser.getEmail())
                    .build();

            DefaultResponse response = DefaultResponse.response(
                    StatusCode.OK,
                    ResponseMessage.USER_SEARCH_SUCCESS,
                    userInfoResponse
            );
            return response;

        }catch (Exception e){
            log.error(e.getMessage());
            return DefaultResponse.response(
                    StatusCode.DB_ERROR,
                    ResponseMessage.USER_SEARCH_FAIL
            );
        }
    }

    //유저 정보(닉네임) 변경 api
    @PatchMapping(value="/user")
    public DefaultResponse updateUser(
            @RequestHeader("Authorization") final String header,
            @RequestBody UpdateUserRequest updateUserRequest
            ){
        try{
            if (header == null){
                return DefaultResponse.response(
                        StatusCode.UNAUTHORIZED,
                        ResponseMessage.UNAUTHORIZED
                );
            }
            Long userId = jwtService.decodeAccessToken(header);
            User findUser = userService.findById(userId);
            userService.updateUser(findUser, updateUserRequest.getNickname());

            //사용자가 공유일기에 작성했던 모든 기록들의 닉네임을 변경해야 할 필요가 있다.
            return DefaultResponse.response(StatusCode.OK, ResponseMessage.USER_UPDATE_SUCCESS);
        }catch (Exception e){
            return DefaultResponse.response(StatusCode.DB_ERROR,ResponseMessage.USER_UPDATE_FAIL);
            }
        }

    // 회원정보 삭제 api
    @DeleteMapping(value = "/user")
    public DefaultResponse deleteUser(
            @RequestHeader("Authorization") final String header
            ){

        try {
            if(header == null){
                return DefaultResponse.response(
                        StatusCode.UNAUTHORIZED,
                        ResponseMessage.UNAUTHORIZED
                );
            }
            Long userId = jwtService.decodeAccessToken(header);
            User findUser = userService.findById(userId);
            userService.deleteUser(findUser.getId());

            return DefaultResponse
                    .response(
                            StatusCode.OK,
                            ResponseMessage.USER_DELETE_SUCCESS
                    );

        } catch (Exception e){
            log.error(e.getMessage());
            return DefaultResponse
                    .response(
                            StatusCode.DB_ERROR,
                            ResponseMessage.USER_DELETE_FAIL
                    );
            }
        }

        //그룹 초대 시, 유저의 리스트를 찾기 위한 api
        @PostMapping(value = "/user/find")
        public DefaultResponse findUser(@RequestBody ContainedUserRequest cur){
            ContainedUserResponse users=  userService.findByContainedUser(cur);
            try{
                return DefaultResponse.response(
                        StatusCode.OK,
                        ResponseMessage.USER_SEARCH_SUCCESS,
                        users
                );
            } catch (Exception e){
                return DefaultResponse.response(
                        StatusCode.DB_ERROR,
                        ResponseMessage.INVALID_USER
                );
            }
        }
    }

