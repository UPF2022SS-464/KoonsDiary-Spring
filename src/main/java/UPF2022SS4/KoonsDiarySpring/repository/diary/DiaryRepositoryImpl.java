package UPF2022SS4.KoonsDiarySpring.repository.diary;

import UPF2022SS4.KoonsDiarySpring.domain.Diary;
import UPF2022SS4.KoonsDiarySpring.domain.QDiary;
import UPF2022SS4.KoonsDiarySpring.domain.QUser;
import UPF2022SS4.KoonsDiarySpring.domain.User;
import UPF2022SS4.KoonsDiarySpring.repository.user.UserJpaRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class DiaryRepositoryImpl implements DiaryRepository{

    private final JPAQueryFactory jqf;
    private EntityManager em;
    private UserJpaRepository userJpaRepository;
    QDiary diary  = QDiary.diary;
    QUser user = QUser.user;

    //도든 다이어리 리스트 반환
    @Override
    public List<Diary> findAllById(Long userId) {
//        Optional<User> findUser = userJpaRepository.findById(userId);
        return jqf.select(diary)
                .from(diary)
                .where(diary.user.id.eq(userId))
                .fetch();
    }

    @Override
    public List<Diary> findAllByUser(User user) {
        return jqf.select(diary)
                .from(diary)
                .where(diary.user.eq(user))
                .fetch();
    }

    @Override
    public void saveDiary(Diary diary, User user) {
        diary.setUser(user);
        em.persist(diary);
    }

    //하나의 다이어리에 대한 정보 반환
//    @Override
//    public Diary findById(Long id) {
//        return jqf.select(diary)
//                .from(diary)
//                .where(diary.id.eq(id))
//                .fetchOne();
//    }
}
