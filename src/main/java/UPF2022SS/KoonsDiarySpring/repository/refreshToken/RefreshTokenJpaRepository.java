package UPF2022SS.KoonsDiarySpring.repository.refreshToken;

import UPF2022SS.KoonsDiarySpring.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenJpaRepository extends JpaRepository<RefreshToken, Long> , RefreshTokenRepository{
}
