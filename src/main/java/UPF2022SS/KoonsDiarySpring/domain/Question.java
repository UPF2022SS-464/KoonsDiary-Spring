package UPF2022SS.KoonsDiarySpring.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;


@Getter
@Table(name = "question")
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Question {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long id;

    @NotBlank
    private String question;
}
