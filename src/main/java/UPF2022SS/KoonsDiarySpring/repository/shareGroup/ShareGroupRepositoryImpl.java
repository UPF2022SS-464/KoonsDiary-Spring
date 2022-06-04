package UPF2022SS.KoonsDiarySpring.repository.shareGroup;


import UPF2022SS.KoonsDiarySpring.domain.*;
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
public class ShareGroupRepositoryImpl  implements  ShareGroupRepository{

    private EntityManager em;

    private final JPAQueryFactory jqf;
    QShareGroup qShareGroup = QShareGroup.shareGroup;
    QUser qUser = QUser.user;


    // 사용자가 들어가 있는 모든 리스트 반환 -> 이건 바꿀 필요가 있다.
    @Override
    public Optional<List<ShareGroup>> findListAll(User user) {
        return Optional.of(jqf
                .selectFrom(qShareGroup)
                .where(qShareGroup.user.eq(user))
                .fetch());
    }
}
