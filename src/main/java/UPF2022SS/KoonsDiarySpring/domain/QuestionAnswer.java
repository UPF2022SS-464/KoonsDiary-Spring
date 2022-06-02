package UPF2022SS.KoonsDiarySpring.domain;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Getter @Setter
@Builder
@Table(name = "question_answer")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class QuestionAnswer {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_answer_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String Content;

    @Builder.Default
    private Integer nice = 0;

    @Builder.Default
    private Integer good = 0;

    @Builder.Default
    private Integer normal = 0;

    @Builder.Default
    private Integer sad = 0;

    @Builder.Default
    private Integer bad = 0;
}
