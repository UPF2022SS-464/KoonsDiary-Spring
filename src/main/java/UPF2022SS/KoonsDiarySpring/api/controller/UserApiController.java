package UPF2022SS.KoonsDiarySpring.api.controller;

import UPF2022SS.KoonsDiarySpring.api.dto.user.*;
import UPF2022SS.KoonsDiarySpring.api.dto.DefaultResponse;
import UPF2022SS.KoonsDiarySpring.api.dto.user.Kakao.AccessDto;
import UPF2022SS.KoonsDiarySpring.common.ResponseMessage;
import UPF2022SS.KoonsDiarySpring.common.StatusCode;
import UPF2022SS.KoonsDiarySpring.domain.ImagePath;
import UPF2022SS.KoonsDiarySpring.domain.RefreshToken;
import UPF2022SS.KoonsDiarySpring.domain.User;
import UPF2022SS.KoonsDiarySpring.service.AuthService;
import UPF2022SS.KoonsDiarySpring.service.JwtService;
import UPF2022SS.KoonsDiarySpring.service.RefreshTokenService;
import UPF2022SS.KoonsDiarySpring.service.image.ImageService;
import UPF2022SS.KoonsDiarySpring.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

import static UPF2022SS.KoonsDiarySpring.api.dto.user.Kakao.*;
import static UPF2022SS.KoonsDiarySpring.api.dto.user.SignUp.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.CONFLICT;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final AuthService authService;
    private final ImageService imageService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    // 아이디 유효성 검증 api
    @PostMapping(value = "/user/validateId")
    public Boolean isUsernameDuplicated(@Valid @RequestBody final ValidationId.Request validationId){
        return userService.validateDuplicateUserId(validationId.getId());
    }

    // 이메일 유효성 검증 api
    @PostMapping(value = "/user/validateEmail")
    public Boolean isEmailDuplicated(@Valid @RequestBody final ValidationEmail.Request validationEmail){
        return userService.validateDuplicateUserEmail(validationEmail.getEmail());
    }

    @PostMapping(value = "/user")
    public ResponseEntity signUpWithAccount(@Valid @RequestBody final Request request){

            if(!userService.validateDuplicateUserId(request.getUserId())){
                return ResponseEntity
                        .status(CONFLICT)
                        .body(ResponseMessage.DUPLICATED_USER);
            }
            else if(!userService.validateDuplicateUserEmail(request.getEmail())){
                return ResponseEntity
                        .status(CONFLICT)
                        .body(ResponseMessage.DUPLICATED_EMAIL);
            }

            //이미지 반환
            Optional<ImagePath> findImage = imageService.findImage(request.getImageId());

            User user = User.builder().username(request.getUserId())
                    .email(request.getEmail())
                    .userPwd(passwordEncoder.encode(request.getPassword()))
                    .nickname(request.getNickname())
                    .imagePath(findImage.get())
                    .build();

            RefreshToken token = new RefreshToken(user, authService.createRefreshToken());
            refreshTokenService.save(token);
            user.setRefreshToken(token);

            //response 반환
            ResponseEntity invalidation = userService.join(user);

            if(invalidation.getStatusCode().equals(CONFLICT)){
                return invalidation;
            }
            return authService.signUpLogin(user, token.getValue());
    }

    /*
     * 카카오 아이디 반환
     */
    @PostMapping(value = "/kakao")
    public ResponseEntity<AccessDto> getKakaoId(@Valid @RequestHeader final String accessToken){
        try{
            ResponseEntity<AccessDto> accessDto = userService.getKakaoId(accessToken);
            return accessDto;
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(value = "/user/kakaoSignUp")
    public ResponseEntity<SignUpResponse> signUpWithKakao(@Valid @RequestBody SignUpRequset signUpRequset){
        try{
            ImagePath imagePath = imageService.findImage(signUpRequset.getImageId()).get();

            User user = User.builder()
                    .username(signUpRequset.getUserId())
                    .userPwd(passwordEncoder.encode(signUpRequset.getPassword()))
                    .nickname(signUpRequset.getNickname())
                    .kakaoId(signUpRequset.getKakaoId())
                    .imagePath(imagePath)
                    .build();

            userService.join(user);

            SignUpResponse signUpResponse = new SignUpResponse(jwtService.createAccessToken(user.getId()));

            return ResponseEntity.ok().body(signUpResponse);
        }
        catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }


    //헤더 지울것
    //회원가입 시 자동로그인, 로그인 시 자동로그인을 위해 리프레시토큰으로 자동로그인, 리퀘스트 로그인
    @PostMapping(value = "/login/account")
    public ResponseEntity<Object> login(@RequestBody final Login.Request request){
            User findUser;
            //아이디 형식이 이메일일 경우
            if (request.getUserId().contains("@")){
                findUser = userService.findUserEmail(request.getUserId());
            }
            //일반 아이디 형식일 경우
            else{
               findUser = userService.findUsername(request.getUserId());
            }

            //찾아낸 사용자가 없을 경우
            if(findUser == null){
                return ResponseEntity
                        .status(UNAUTHORIZED)
                        .body(ResponseMessage.INVALID_USER);
                }

            authService.checkExpirationDate(findUser);
            return authService.requestLogin(request, findUser.getRefreshToken().getValue());
        }

        // 토큰을 통한 로그인
        @GetMapping(value = "/login/token")
        public ResponseEntity<Object> tokenLogin(@RequestHeader("Authorization") final String header){
        try{
            if (header == null) {
                return ResponseEntity
                        .badRequest()
                        .body(ResponseMessage.BAD_REQUEST);
            }

            return authService.tokenLogin(header);

        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity
                    .badRequest()
                    .body(ResponseMessage.BAD_REQUEST);
            }
        }

        //카카오 아아디를 통한 로그인
       @GetMapping(value = "/login/kakao")
        public ResponseEntity<SignInResponse> signInWithKakao(@RequestBody final SignInRequset requset){
            try{
                User user = userService.findUserKakaoId(requset.getKakaoId());

                SignInResponse signInResponse = new SignInResponse(jwtService.createAccessToken(user.getId()));

                return ResponseEntity.ok().body(signInResponse);
            }
            catch (Exception e){
                log.error(e.getMessage());
                return ResponseEntity.badRequest().build();
            }
        }



    //유저 정보 get api
    @GetMapping(value = "/user")
    public ResponseEntity<Object> getUser(@RequestHeader("Authorization") final String header){
        try{
            if (header == null){
                return ResponseEntity
                        .status(UNAUTHORIZED)
                        .body(ResponseMessage.UNAUTHORIZED);
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
            return ResponseEntity.ok().body(response);

        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity
                    .internalServerError()
                    .body(ResponseMessage.INTERNAL_SERVER_ERROR);
        }
    }



    //유저 정보(닉네임, 비밀번호, 이미지) 변경 api
    @PatchMapping(value="/user")
    public ResponseEntity<Object> updateUser(
            @RequestHeader("Authorization") final String header,
            @RequestBody UpdateUser.Request request
            ){
        try{
            if (header == null){
                return ResponseEntity
                        .status(UNAUTHORIZED)
                        .body(ResponseMessage.UNAUTHORIZED);
            }

            Long userId = jwtService.decodeAccessToken(header);
            User findUser = userService.findById(userId);

            userService.updateUser(findUser, request);

            String result = "정보가 업데이트 되었습니다.";

            UpdateUser.Response response = new UpdateUser.Response(result);

            //사용자가 공유일기에 작성했던 모든 기록들의 닉네임을 변경해야 할 필요가 있다.
            return ResponseEntity.ok().body(response);
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(ResponseMessage.USER_UPDATE_FAIL);
            }
        }

    // 회원정보 삭제 api
    @DeleteMapping(value = "/user")
    public ResponseEntity<Object> deleteUser(
            @RequestHeader("Authorization") final String header
            ){

        try {
            if(header == null){
                return ResponseEntity.badRequest().body(ResponseMessage.BAD_REQUEST);
            }
            Long userId = jwtService.decodeAccessToken(header);
            User findUser = userService.findById(userId);
            userService.deleteUser(findUser.getId());
            String result = "삭제가 완료되었습니다.";

            DeleteUser.Response response = new DeleteUser.Response(result);
            return ResponseEntity.ok().body(ResponseMessage.USER_DELETE_SUCCESS);
        } catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(ResponseMessage.BAD_REQUEST);
            }
        }

        //그룹 초대 시, 유저의 리스트를 찾기 위한 api
        @PostMapping(value = "/user/find")
        public ResponseEntity<Object> findUser(@RequestBody ContainedUserRequest cur){
            ContainedUserResponse users=  userService.findByContainedUser(cur);
            try{
                return ResponseEntity
                        .ok()
                        .body(users);

            } catch (Exception e){
                log.error(e.getMessage());
                return ResponseEntity
                        .status(I_AM_A_TEAPOT).
                        body(ResponseMessage.USER_SEARCH_FAIL);
            }
        }
    }

