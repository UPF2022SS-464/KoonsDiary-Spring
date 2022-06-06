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


}
