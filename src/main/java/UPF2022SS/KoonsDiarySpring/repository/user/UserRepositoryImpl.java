
package UPF2022SS.KoonsDiarySpring.repository.user;

import UPF2022SS.KoonsDiarySpring.domain.QUser;
import UPF2022SS.KoonsDiarySpring.domain.User;
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
    //111
    QUser user  = QUser.user;

    @Override
    public List<User> findByContainedName(String username) {
        return jqf.select(user)
                .from(user)
                .where(user.nickname.contains(username))
                .fetch();
    }
}
