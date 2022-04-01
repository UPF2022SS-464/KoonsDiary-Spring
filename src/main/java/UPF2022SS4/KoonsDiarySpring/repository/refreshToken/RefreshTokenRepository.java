package UPF2022SS4.KoonsDiarySpring.repository.refreshToken;

import UPF2022SS4.KoonsDiarySpring.domain.RefreshToken;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository {
    public RefreshToken findByValue(String tokenValue);
}
