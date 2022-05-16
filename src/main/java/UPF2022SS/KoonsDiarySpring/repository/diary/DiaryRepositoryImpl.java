package UPF2022SS.KoonsDiarySpring.repository.diary;

import UPF2022SS.KoonsDiarySpring.api.dto.diary.MonthlyDiary;
import UPF2022SS.KoonsDiarySpring.repository.user.UserJpaRepository;
import UPF2022SS.KoonsDiarySpring.domain.Diary;
import UPF2022SS.KoonsDiarySpring.domain.QDiary;
import UPF2022SS.KoonsDiarySpring.domain.QUser;
import UPF2022SS.KoonsDiarySpring.domain.User;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class DiaryRepositoryImpl implements DiaryRepository{

    private final JPAQueryFactory jqf;
    private EntityManager em;
    private UserJpaRepository userJpaRepository;
    QDiary qdiary = QDiary.diary;

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



    @Override
    public List<MonthlyDiary> findListByMonth(Long userId, LocalDate startDate, LocalDate endDate) {
        //월별 조회를 위한 날짜 형태 지정
        StringTemplate formattedDate = Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})"
                , qdiary.writeDate
                , ConstantImpl.create("%Y-%m"));

        return jqf
                .select(
                        Projections.constructor(MonthlyDiary.class,
                                qdiary.id,
                                qdiary.writeDate,
                                qdiary.emotion
                                ))
                .from(qdiary)
                .where(
                        qdiary.user.id.eq(userId),
                        qdiary.writeDate.goe(startDate),
                        qdiary.writeDate.loe(endDate)
                )
//                .groupBy(formattedDate)
//                .orderBy(formattedDate.asc())
                .fetch();
    }
}
