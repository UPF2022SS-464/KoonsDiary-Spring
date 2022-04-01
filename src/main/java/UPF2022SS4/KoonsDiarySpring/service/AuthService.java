package UPF2022SS4.KoonsDiarySpring.service;


import UPF2022SS4.KoonsDiarySpring.api.dto.DefaultResponse;
import UPF2022SS4.KoonsDiarySpring.api.dto.user.LoginRequest;
import UPF2022SS4.KoonsDiarySpring.api.dto.user.LoginResponse;
import UPF2022SS4.KoonsDiarySpring.api.dto.user.SignUpRequest;
import UPF2022SS4.KoonsDiarySpring.api.dto.user.SignUpResponse;
import UPF2022SS4.KoonsDiarySpring.common.ResponseMessage;
import UPF2022SS4.KoonsDiarySpring.common.StatusCode;
import UPF2022SS4.KoonsDiarySpring.domain.User;
import UPF2022SS4.KoonsDiarySpring.repository.user.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserJpaRepository userJpaRepository;
    private final JwtService jwtService;

    //회원가입시 로그인까지 함께 전달
    public DefaultResponse signUpLogin(final SignUpRequest signUpRequest, String refreshTokenDto){
        final User user = userJpaRepository.findByName(signUpRequest.getUserId());

        //비밀번호 일치 여부에 대한 부분은 추가해야된다.

        // Access, refresh token을 생성 후 , 메시지 응답과 함께 전달
        final String accessTokenDto = jwtService.createAccessToken(user.getId());

        SignUpResponse signUpResponse = new SignUpResponse(
                accessTokenDto,
                refreshTokenDto,
                user.getUsername()
        );
        return DefaultResponse.response(StatusCode.OK, ResponseMessage.LOGIN_SUCCESS, signUpResponse);
    }

    public String createRefreshToken(){
        return jwtService.createRefreshToken();
    }

    //만료 이전일 경우 참, 만료의 경우 거짓 반환
    public boolean checkExpirationDate(String token){

        LocalDate tokenDate = jwtService.decodeRefreshToken(token);
        LocalDate now =  LocalDate.now();
        return tokenDate.isBefore(now);
    }
    /*
    1차적으로 유저의 아이디와 비밀번호를 대조
    2차적으로 refresh token의 만료기간 확인

    만료시 refresh token 재발급
    만료 이전일 경우 access token 발급

     */
    public DefaultResponse login(final LoginRequest loginRequest, String refreshToken){
        final User user = userJpaRepository.findByName(loginRequest.getUserId());

        try{
            if (user.getPassword().equals(loginRequest.getPassword())){
            }

        }catch (Exception e){
            return DefaultResponse.response(StatusCode.UNAUTHORIZED, ResponseMessage.LOGIN_FAIL);
        }


        //회원이 존재하지 않을 경우에 대한 응답
        if(user == null){
            return DefaultResponse.builder()
                    .status(StatusCode.UNAUTHORIZED)
                    .message(ResponseMessage.NOT_FOUND_USER)
                    .build();
        }

        //비밀번호 일치 여부에 대한 부분은 추가해야된다.

        //토큰을 생성 후 , 메시지 응답과 함께 전달
        final String accessToken = jwtService.createAccessToken(user.getId());
        LoginResponse loginResponse = new LoginResponse(user.getUsername(), accessToken, refreshToken);
        return DefaultResponse.builder()
                .status(StatusCode.OK)
                .message(ResponseMessage.LOGIN_SUCCESS)
                .data(loginResponse)
                .build();
//        return DefaultResponse.response(StatusCode.OK, ResponseMessage.LOGIN_SUCCESS, loginResponse);
    }
}
