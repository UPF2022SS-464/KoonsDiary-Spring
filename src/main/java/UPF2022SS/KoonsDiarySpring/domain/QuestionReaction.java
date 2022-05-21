package UPF2022SS.KoonsDiarySpring.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Getter
@Setter
@Table(name = "question_reaction")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class QuestionReaction {
//    GOOD, HEART, CLAP, NOTE, VICTORY
    @Id
    @Column(name = "question_answer_id", nullable = false)
    private Long id;

    @OneToOne(fetch = LAZY)
    @MapsId // @id 어노테이션이 선언된 부분에 관계를 매핑시키는 역할
    @JoinColumn(name = "question_answer_id")
    private QuestionAnswer questionAnswer;

    Integer nice;

    Integer good;

    Integer normal;

    Integer sad;

    Integer bad;

}
