package UPF2022SS.KoonsDiarySpring.repository.image;

import UPF2022SS.KoonsDiarySpring.domain.Image;
import UPF2022SS.KoonsDiarySpring.domain.QImage;
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
public class ImageRepositoryImpl implements ImageRepository{

    private final JPAQueryFactory jqf;
    private EntityManager em;
    QImage qImage = QImage.image;

    @Override
    public Optional<List<Image>> findAll() {
        return Optional.ofNullable(jqf.selectFrom(qImage)
                .fetch()
        );
    }

    @Override
    public Optional<Image> findByPath(String path) {
        return Optional.ofNullable(
                jqf.selectFrom(qImage)
                        .where(qImage.path.eq(path))
                        .fetchOne()
        );
    }
}
