package UPF2022SS4.KoonsDiarySpring.service;


import UPF2022SS4.KoonsDiarySpring.api.dto.DefaultResponse;
import UPF2022SS4.KoonsDiarySpring.api.dto.user.LoginRequest;
import UPF2022SS4.KoonsDiarySpring.api.dto.user.LoginResponse;
import UPF2022SS4.KoonsDiarySpring.api.dto.user.SignUpRequest;
import UPF2022SS4.KoonsDiarySpring.api.dto.user.SignUpResponse;
import UPF2022SS4.KoonsDiarySpring.common.ResponseMessage;
import UPF2022SS4.KoonsDiarySpring.common.StatusCode;
import UPF2022SS4.KoonsDiarySpring.domain.User;
import UPF2022SS4.KoonsDiarySpring.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserJpaRepository userJpaRepository;
    private final JwtService jwtService;

    public DefaultResponse signUpLogin(final SignUpRequest signUpRequest){
        final User user = userJpaRepository.findByName(signUpRequest.getUserId());

        //비밀번호 일치 여부에 대한 부분은 추가해야된다.

        //토큰을 생성 후 , 메시지 응답과 함께 전달
        final String tokenDto = jwtService.create(user.getId());

        SignUpResponse signUpResponse = new SignUpResponse(
                tokenDto,
                user.getUsername()
        );
        return DefaultResponse.response(StatusCode.OK, ResponseMessage.LOGIN_SUCCESS, signUpResponse);
    }


    public DefaultResponse login(final LoginRequest loginRequest){
        final User user = userJpaRepository.findByName(loginRequest.getUserId());

        //회원이 존재하지 않을 경우에 대한 응답
        if(user == null){
            return DefaultResponse.builder()
                    .status(StatusCode.UNAUTHORIZED)
                    .message(ResponseMessage.NOT_FOUND_USER)
                    .build();
        }

        //비밀번호 일치 여부에 대한 부분은 추가해야된다.

        //토큰을 생성 후 , 메시지 응답과 함께 전달
        final String tokenDto = jwtService.create(user.getId());
        LoginResponse loginResponse = new LoginResponse(user.getId(), user.getToken(), user.getUsername());
        return DefaultResponse.response(StatusCode.OK, ResponseMessage.LOGIN_SUCCESS, loginResponse);
    }
}
