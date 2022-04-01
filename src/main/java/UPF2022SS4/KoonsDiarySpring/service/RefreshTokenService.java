package UPF2022SS4.KoonsDiarySpring.service;

import UPF2022SS4.KoonsDiarySpring.api.dto.DefaultResponse;
import UPF2022SS4.KoonsDiarySpring.domain.RefreshToken;
import org.springframework.stereotype.Service;

@Service
public interface RefreshTokenService {
    public DefaultResponse save(RefreshToken token);

    public String updateToken(String token);

    public RefreshToken getToken(String RefreshTokenValue);
}
