package UPF2022SS.KoonsDiarySpring.service;


import UPF2022SS.KoonsDiarySpring.api.dto.DefaultResponse;
import UPF2022SS.KoonsDiarySpring.api.dto.user.LoginRequest;
import UPF2022SS.KoonsDiarySpring.api.dto.user.LoginResponse;
import UPF2022SS.KoonsDiarySpring.api.dto.user.SignUpResponse;
import UPF2022SS.KoonsDiarySpring.domain.RefreshToken;
import UPF2022SS.KoonsDiarySpring.repository.refreshToken.RefreshTokenJpaRepository;
import UPF2022SS.KoonsDiarySpring.repository.user.UserJpaRepository;
import UPF2022SS.KoonsDiarySpring.common.ResponseMessage;
import UPF2022SS.KoonsDiarySpring.common.StatusCode;
import UPF2022SS.KoonsDiarySpring.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {


    private final UserJpaRepository userJpaRepository;

    private final RefreshTokenJpaRepository refreshTokenJpaRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    //회원가입시 로그인까지 함께 전달
    public DefaultResponse signUpLogin(final User user, String refreshTokenDto) {
        // Access, refresh token을 생성 후 , 메시지 응답과 함께 전달
        final String accessTokenDto = jwtService.createAccessToken(user.getId());

        SignUpResponse signUpResponse = new SignUpResponse(
                accessTokenDto,
                refreshTokenDto,
                user.getUsername()
        );
        return DefaultResponse.response(
                StatusCode.OK,
                ResponseMessage.USER_CREATE_SUCCESS,
                signUpResponse
        );
    }

    public String createRefreshToken() {
        return jwtService.createRefreshToken();
    }

    //만료 이전일 경우 참, 만료의 경우 거짓 반환
    public void checkExpirationDate(User user) {
        //토큰 해독 후 날짜 확인
        LocalDate tokenDate = jwtService.decodeRefreshToken(user.getRefreshToken().getValue());
        LocalDate now = LocalDate.now();
        // 만료 이후일 경우
        if (tokenDate.isAfter(now)){
            RefreshToken newToken = new RefreshToken(user, createRefreshToken());
            user.setRefreshToken(newToken);
        }
    }

    /*
    1차적으로 유저의 아이디와 비밀번호를 대조
    2차적으로 refresh token의 만료기간 확인

    만료시 refresh token 재발급
    만료 이전일 경우 access token 발급
     */
    public DefaultResponse requestLogin(final LoginRequest loginRequest, String refreshToken) {

        final User user = userJpaRepository.findByName(loginRequest.getUserId());

//        //회원이 존재하지 않을 경우에 대한 응답
//        if (user == null) {
//            return DefaultResponse.builder()
//                    .status(StatusCode.UNAUTHORIZED)
//                    .message(ResponseMessage.NOT_FOUND_USER)
//                    .build();
//        }

        try {
            //비밀번호 일치 여부에 대한 부분
            if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                //토큰을 생성 후 , 메시지 응답과 함께 전달
                final String accessToken = jwtService.createAccessToken(user.getId());
                LoginResponse loginResponse = new LoginResponse(
                        user.getId(),
                        user.getUsername(),
                        accessToken,
                        refreshToken
                );
                return DefaultResponse.builder()
                        .status(StatusCode.OK)
                        .message(ResponseMessage.LOGIN_SUCCESS)
                        .data(loginResponse)
                        .build();
                }
                return DefaultResponse.builder()
                        .status(StatusCode.UNAUTHORIZED)
                        .message(ResponseMessage.LOGIN_FAIL)
                        .build();

            } catch (Exception e) {
            return DefaultResponse.response(
                    StatusCode.UNAUTHORIZED,
                    ResponseMessage.LOGIN_FAIL
                );
        }
    }

    public  DefaultResponse tokenLogin(final String token){
     try{
         RefreshToken refreshToken = refreshTokenJpaRepository.findByValue(token);

        if(refreshToken == null){
            return DefaultResponse.builder()
                    .status(StatusCode.BAD_REQUEST)
                    .message(ResponseMessage.BAD_REQUEST)
                    .data(refreshToken)
                    .build();
        }

         User user = refreshToken.getUser();

         //회원이 존재하지 않을 경우에 대한 응답
        if (user == null) {
            return DefaultResponse.builder()
                    .status(StatusCode.UNAUTHORIZED)
                    .message(ResponseMessage.UNAUTHORIZED)
                    .build();
        }

         checkExpirationDate(user);

         final String accessToken = jwtService.createAccessToken(user.getId());

         LoginResponse loginResponse = new LoginResponse(
                 user.getId(),
                 user.getUsername(),
                 accessToken,
                 user.getRefreshToken().getValue()
         );
         return DefaultResponse.builder()
                 .status(StatusCode.OK)
                 .message(ResponseMessage.LOGIN_SUCCESS)
                 .data(loginResponse)
                 .build();
     }catch (Exception e){
        log.error(e.getMessage());
        return DefaultResponse.response(
                StatusCode.INTERNAL_SERVER_ERROR,
                ResponseMessage.INTERNAL_SERVER_ERROR,
                e.getMessage()
        );
     }
    }
}
