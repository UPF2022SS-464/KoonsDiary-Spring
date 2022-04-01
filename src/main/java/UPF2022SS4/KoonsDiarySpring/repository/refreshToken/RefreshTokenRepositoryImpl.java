package UPF2022SS4.KoonsDiarySpring.repository.refreshToken;

import UPF2022SS4.KoonsDiarySpring.domain.QRefreshToken;
import UPF2022SS4.KoonsDiarySpring.domain.RefreshToken;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Slf4j
@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository{


    private EntityManager em;

//    private final JPAQueryFactory jqf;
//    QRefreshToken refreshToken = QRefreshToken.refreshToken;

    @Override
    public RefreshToken findByValue(String tokenValue) {
        RefreshToken findToken = em.find(RefreshToken.class, tokenValue);
        return findToken;
    }

    }
