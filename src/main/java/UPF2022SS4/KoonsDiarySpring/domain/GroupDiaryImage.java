package UPF2022SS4.KoonsDiarySpring.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "group_diary_image")
public class GroupDiaryImage {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_diary_image")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "group_diary_id")
    private GroupDiary groupDiary;

    @Column(nullable = false)
    private String groupDiaryImagePath;

    private String groupDiaryComment;
}
