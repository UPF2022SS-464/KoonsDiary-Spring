package UPF2022SS.KoonsDiarySpring.repository.questionAnswer;

import UPF2022SS.KoonsDiarySpring.domain.QuestionAnswer;
import UPF2022SS.KoonsDiarySpring.repository.question.QuestionRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionAnswerJpaRepository extends JpaRepository<QuestionAnswer, String>, QuestionAnswerRepository {



}
