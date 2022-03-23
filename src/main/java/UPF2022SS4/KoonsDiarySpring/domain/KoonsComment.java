package UPF2022SS4.KoonsDiarySpring.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "koons_comment")
public class KoonsComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int emotion;

    private String comment;
}
