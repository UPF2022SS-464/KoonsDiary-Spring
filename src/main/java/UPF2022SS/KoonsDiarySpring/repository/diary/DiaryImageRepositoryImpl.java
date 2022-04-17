package UPF2022SS.KoonsDiarySpring.repository.diary;

import UPF2022SS.KoonsDiarySpring.domain.DiaryImage;
import UPF2022SS.KoonsDiarySpring.domain.QDiary;
import UPF2022SS.KoonsDiarySpring.domain.QDiaryImage;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class DiaryImageRepositoryImpl implements DiaryImageRepository{

    private final JPAQueryFactory jqf;

    private DiaryJpaRepository diaryJpaRepository;
    QDiary qDiary = QDiary.diary;
    QDiaryImage qDiaryImage = QDiaryImage.diaryImage;

    @Override
    public List<DiaryImage> findByDiaryId(Long DiaryId) {
        return jqf.select(qDiaryImage)
                .from(qDiary)
                .where(qDiary.id.eq(DiaryId))
                .fetch();
    }
}
