package UPF2022SS.KoonsDiarySpring.api.controller;

import UPF2022SS.KoonsDiarySpring.api.dto.user.*;


import UPF2022SS.KoonsDiarySpring.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Slf4j
@RestController
@RequiredArgsConstructor
public class UserApiController {

    //의존성 주입
    private final UserService userService;

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

    @PostMapping(value = "/user/kakaoSignUp")
    public KakaoDto.Create.ResponseDto signUpWithKakao(@Valid @RequestBody KakaoDto.Create.RequestDto requestDto) {
        return userService.createKakao(requestDto);
    }

    //헤더 지울것
    //회원가입 시 자동로그인, 로그인 시 자동로그인을 위해 리프레시토큰으로 자동로그인, 리퀘스트 로그인
    @PostMapping(value = "/login/account")
    public UserDto.Read.ResponseDto login(@RequestBody final UserDto.Read.RequestDto requestDto){
        return userService.readV1(requestDto);
        }

//    // 토큰을 통한 로그인
//    @GetMapping(value = "/login/token")
//    public ResponseEntity<Object> tokenLogin(@RequestHeader("Authorization") final String header){
//    try{
//        if (header == null) {
//            return ResponseEntity
//                    .badRequest()
//                    .body(ResponseMessage.BAD_REQUEST);
//        }
//
//        return authService.tokenLogin(header);
//
//        }catch (Exception e){
//            log.error(e.getMessage());
//            return ResponseEntity
//                    .badRequest()
//                    .body(ResponseMessage.BAD_REQUEST);
//            }
//        }

    //카카오 아아디를 통한 로그인
    @GetMapping(value = "/login/kakao")
    public KakaoDto.Read.ResponseDto signInWithKakao(@RequestHeader final String token) {
        return userService.readKako(token);
    }


    //유저 정보 get api
    @GetMapping(value = "/user")
    public UserDto.Read.ResponseDtoV2 getUser(@RequestHeader("Authorization") final String header) {
        return userService.read(header);
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
    @PostMapping(value = "/user/find/{nickname}")
    public UserDto.Search.ResponseDto findUser(@PathVariable final String nickname) {
        return userService.findByContainedUser(nickname);
        }
    }

