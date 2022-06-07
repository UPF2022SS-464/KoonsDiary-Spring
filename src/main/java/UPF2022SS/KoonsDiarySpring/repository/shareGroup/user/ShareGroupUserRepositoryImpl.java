package UPF2022SS.KoonsDiarySpring.repository.shareGroup.user;

import UPF2022SS.KoonsDiarySpring.domain.QShareGroupUser;
import UPF2022SS.KoonsDiarySpring.domain.ShareGroup;
import UPF2022SS.KoonsDiarySpring.domain.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ShareGroupUserRepositoryImpl implements ShareGroupUserRepository {

    private final EntityManager em;
    private final JPAQueryFactory jqf;

    QShareGroupUser qShareGroupUser = QShareGroupUser.shareGroupUser;

    @Override
    public Optional<List<ShareGroup>> findByUser(User user) {
        return Optional.ofNullable(
                jqf.select(qShareGroupUser.shareGroup)
                        .from(qShareGroupUser)
                        .where(qShareGroupUser.user.eq(user))
                        .fetch());
    }
}
