
package UPF2022SS4.KoonsDiarySpring.repository;

import UPF2022SS4.KoonsDiarySpring.domain.QUser;
import UPF2022SS4.KoonsDiarySpring.domain.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final EntityManager em;
    private final JPAQueryFactory jqf;

    QUser user  = QUser.user;


    @Override
    public List<User> findByKakaoKey(Long kakaoKey) {
        return jqf.select(user)
                .from(user)
                .where(user.kakaoKey.eq(kakaoKey))
                .fetch();
    }

    @Override
    public List<User> findByName(String userName) {
        return jqf.selectFrom(user)
                .where(user.userid.eq(userName))
                .fetch();
    }

    @Override
    public List<User> findByEmail(String email){
        return jqf.select(user)
                .from(user)
                .where(user.email.eq(email))
                .fetch();
    }

    @Override
    public List<User> findAll() {
        return jqf.selectFrom(user)
                .fetch();
    }
}
