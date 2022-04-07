package UPF2022SS.KoonsDiarySpring.service;

import UPF2022SS.KoonsDiarySpring.api.dto.DefaultResponse;
import UPF2022SS.KoonsDiarySpring.domain.RefreshToken;
import org.springframework.stereotype.Service;

@Service
public interface RefreshTokenService {
    public DefaultResponse save(RefreshToken token);

    public String updateToken(String token);

    public RefreshToken getToken(String RefreshTokenValue);
}
