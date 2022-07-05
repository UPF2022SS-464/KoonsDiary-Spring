package UPF2022SS.KoonsDiarySpring.api.controller;

import UPF2022SS.KoonsDiarySpring.api.dto.user.*;
import UPF2022SS.KoonsDiarySpring.api.dto.DefaultResponse;
import UPF2022SS.KoonsDiarySpring.api.dto.user.Kakao.AccessDto;
import UPF2022SS.KoonsDiarySpring.common.ResponseMessage;
import UPF2022SS.KoonsDiarySpring.common.StatusCode;
import UPF2022SS.KoonsDiarySpring.domain.ImagePath;
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

import static UPF2022SS.KoonsDiarySpring.api.dto.user.Kakao.*;
import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserApiController {

    //의존성 주입
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

    /*
    * 회원가입 api
    * */
    @PostMapping(value = "/user")
    public UserDto.Create.ResponseDto signUpWithAccount(@Valid @RequestBody final UserDto.Create.RequestDto requestDto){
        return userService.create(requestDto);
    }

    /*
     * 카카오 아이디 반환
     */
    @PostMapping(value = "/user/kakao")
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

            UPF2022SS.KoonsDiarySpring.domain.User user = UPF2022SS.KoonsDiarySpring.domain.User.builder()
                    .username(signUpRequset.getUserId())
                    .password(passwordEncoder.encode(signUpRequset.getPassword()))
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
    public UserDto.Read.ResponseDto login(@RequestBody final UserDto.Read.RequestDto requestDto){
        return userService.readV1(requestDto);
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
                UPF2022SS.KoonsDiarySpring.domain.User user = userService.findUserKakaoId(requset.getKakaoId());

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
            UPF2022SS.KoonsDiarySpring.domain.User findUser = userService.findById(userId);

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
    public UserDto.Update.ResponseDto updateUser(
            @RequestHeader("Authorization") final String token,
            @RequestBody UserDto.Update.RequestDto requestDto
            ){
            return userService.update(token, requestDto);
        }

    // 회원정보 삭제 api
    @DeleteMapping(value = "/user")
    public UserDto.Delete.ResponseDto deleteUser(
            @RequestHeader("Authorization") final String token
            ){
            return userService.delete(token);
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

