package UPF2022SS.KoonsDiarySpring.repository.question;

import UPF2022SS.KoonsDiarySpring.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionJpaRepository extends JpaRepository<Question, String>, QuestionRepository{
}
