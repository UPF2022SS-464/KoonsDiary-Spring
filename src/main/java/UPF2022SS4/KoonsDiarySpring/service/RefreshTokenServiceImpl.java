package UPF2022SS4.KoonsDiarySpring.service;

import UPF2022SS4.KoonsDiarySpring.api.dto.DefaultResponse;
import UPF2022SS4.KoonsDiarySpring.common.ResponseMessage;
import UPF2022SS4.KoonsDiarySpring.common.StatusCode;
import UPF2022SS4.KoonsDiarySpring.domain.RefreshToken;
import UPF2022SS4.KoonsDiarySpring.repository.refreshToken.RefreshTokenJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService{

    private final RefreshTokenJpaRepository refreshTokenJpaRepository;
    private final JwtService jwtService;

    //토큰 저장 서비스
    @Override
    @Transactional
    public DefaultResponse save(RefreshToken token) {
        refreshTokenJpaRepository.save(token);
        return DefaultResponse.response(StatusCode.OK, ResponseMessage.USER_CREATE_SUCCESS, token);
    }

    @Override
    public String updateToken(String token) {
        RefreshToken findToken = refreshTokenJpaRepository.findByValue(token);
        String renewalToken = jwtService.createRefreshToken();
        findToken.renewalRefreshToken(renewalToken);
        return renewalToken;
    }

    @Override
    public RefreshToken getToken(String RefreshTokenValue) {
        return null;
    }
}
