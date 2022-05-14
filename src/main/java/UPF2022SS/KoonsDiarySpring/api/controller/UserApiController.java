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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final AuthService authService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping(value = "/user")
    public DefaultResponse signUp(@RequestBody final SignUpRequest signUpRequest){
        try{
            User user = User.builder().username(signUpRequest.getUserId())
                    .email(signUpRequest.getEmail())
                    .password(passwordEncoder.encode(signUpRequest.getPassword()))
                    .nickname(signUpRequest.getNickname())
                    .imagePath(signUpRequest.getImagePath())
                    .build();

            //response 반환
            DefaultResponse invalidation = userService.join(user);

            if(invalidation.getStatus() == 409 || invalidation.getStatus()==600)
                return invalidation;

            RefreshToken token = new RefreshToken(user, authService.createRefreshToken());
            refreshTokenService.save(token);
            user.setRefreshToken(token);


            DefaultResponse response = authService.signUpLogin(user, token.getValue());

            return response;

        } catch (Exception e){
            log.error(e.getMessage());
            return DefaultResponse.response(
                    StatusCode.INTERNAL_SERVER_ERROR,
                    ResponseMessage.INTERNAL_SERVER_ERROR,
                    e.getMessage()
            );
        }
    }
    /*
     * 유저네임, 닉네임, 이미지 아이디
     * 카카오 로그인
     */
    @PostMapping(value = "/kakaoUser")
    public DefaultResponse kakaoSignUp(@RequestBody final KakaoSignUpRequest kakaoSignUpRequest){
        try{
            User user = User.builder().username(kakaoSignUpRequest.getUserId())
                    .nickname(kakaoSignUpRequest.getNickname())
                    .imagePath(kakaoSignUpRequest.getImagePath())
                    .build();

            //response 반환
            DefaultResponse invalidation = userService.kakaoJoin(user);

            if(invalidation.getStatus() == 409 || invalidation.getStatus()==600)
                return invalidation;

            RefreshToken token = new RefreshToken(user, authService.createRefreshToken());
            user.setRefreshToken(token);

            DefaultResponse response = authService.signUpLogin(user, token.getValue());

            return response;

        } catch (Exception e){
            log.error(e.getMessage());
            return DefaultResponse.response(
                    StatusCode.INTERNAL_SERVER_ERROR,
                    ResponseMessage.NOT_CONTENT,
                    e.getMessage()
            );
        }
    }

    //헤더 지울것
    //회원가입 시 자동로그인, 로그인 시 자동로그인을 위해 리프레시토큰으로 자동로그인, 리퀘스트 로그인
    @PostMapping(value = "/requestLogin")
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
                        ResponseMessage.NOT_FOUND_USER
                    );
                }

            authService.checkExpirationDate(findUser);
            return authService.requestLogin(loginRequest, findUser.getRefreshToken().getValue());
            }
            catch (Exception e){
                log.error(e.getMessage());

                return DefaultResponse.response(
                        StatusCode.INTERNAL_SERVER_ERROR,
                        ResponseMessage.INTERNAL_SERVER_ERROR);
            }
        }

        //토큰을 통한 로그인
        @GetMapping(value = "/tokenLogin")
        public DefaultResponse tokenLogin(@RequestHeader("Authorization") final String header){
        try{
            if (header == null) {
                return DefaultResponse.response(
                        StatusCode.BAD_REQUEST,
                        ResponseMessage.BAD_REQUEST
                );
            }
            DefaultResponse response = authService.tokenLogin(header);
            return response;

        }catch (Exception e){
            log.error(e.getMessage());
            return DefaultResponse.response(
                    StatusCode.DB_ERROR,
                    ResponseMessage.DB_ERROR);
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

