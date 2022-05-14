package UPF2022SS.KoonsDiarySpring.repository.refreshToken;

import UPF2022SS.KoonsDiarySpring.domain.QRefreshToken;
import UPF2022SS.KoonsDiarySpring.domain.RefreshToken;
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

    private final JPAQueryFactory jqf;
    QRefreshToken refreshToken = QRefreshToken.refreshToken;

    @Override
    public RefreshToken findByValue(String tokenValue) {
        return jqf.selectFrom(refreshToken)
                .where(refreshToken.value.eq(tokenValue))
                .fetchOne();
        }
    }

