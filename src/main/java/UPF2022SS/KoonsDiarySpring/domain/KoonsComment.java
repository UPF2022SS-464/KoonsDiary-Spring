package UPF2022SS.KoonsDiarySpring.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter
@Table(name = "koons_comment")
@Entity
public class KoonsComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int emotion;

    private String comment;
}
