package UPF2022SS.KoonsDiarySpring.service;

import UPF2022SS.KoonsDiarySpring.domain.RefreshToken;
import UPF2022SS.KoonsDiarySpring.repository.refreshToken.RefreshTokenJpaRepository;
import UPF2022SS.KoonsDiarySpring.repository.user.UserJpaRepository;
import UPF2022SS.KoonsDiarySpring.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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


    public String createRefreshToken() {
        return jwtService.createRefreshToken();
    }

    //만료 이전일 경우 참, 만료의 경우 거짓 반환
    @Transactional
    public void checkExpirationDate(User user) {
        //토큰 해독 후 날짜 확인
        LocalDate tokenDate = jwtService.decodeRefreshToken(user.getRefreshToken().getValue());
        LocalDate now = LocalDate.now();
        // 만료 이후일 경우
        if (now.isAfter(tokenDate)){
            RefreshToken refreshToken = user.getRefreshToken();
            refreshToken.updateRefreshToken(new RefreshToken(user, createRefreshToken()).getValue());
            refreshTokenJpaRepository.save(refreshToken);
        }
    }
}
