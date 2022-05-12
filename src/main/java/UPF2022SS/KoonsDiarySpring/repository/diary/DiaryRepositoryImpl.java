package UPF2022SS.KoonsDiarySpring.repository.diary;

import UPF2022SS.KoonsDiarySpring.repository.user.UserJpaRepository;
import UPF2022SS.KoonsDiarySpring.domain.Diary;
import UPF2022SS.KoonsDiarySpring.domain.QDiary;
import UPF2022SS.KoonsDiarySpring.domain.QUser;
import UPF2022SS.KoonsDiarySpring.domain.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class DiaryRepositoryImpl implements DiaryRepository{

    private final JPAQueryFactory jqf;
    private EntityManager em;
    private UserJpaRepository userJpaRepository;
    QDiary qdiary = QDiary.diary;
    QUser quser = QUser.user;

    //모든 다이어리 리스트 반환
    @Override
    public List<Diary> findAllById(Long userId) {
        return jqf.select(qdiary)
                .from(qdiary)
                .where(qdiary.user.id.eq(userId))
                .fetch();
    }

    // 특정 날짜 구간의 다이어리 반환
    @Override
    public List<Diary> findListByLocalDate(Long userId, LocalDate startDate, LocalDate endDate) {
        return jqf.select(qdiary)
                .from(qdiary)
                .where(qdiary.user.id.eq(userId))
                .where(qdiary.writeDate.between(startDate, endDate))
                .fetch();
    }

    // 특정 날짜의 다이어리 반환
    @Override
    public Diary findByLocalDate(Long userId, LocalDate localDate){
        return jqf.select(qdiary)
                .from(qdiary)
                .where(qdiary.user.id.eq(userId))
                .where(qdiary.writeDate.eq(localDate))
                .fetchOne();
    }
}
