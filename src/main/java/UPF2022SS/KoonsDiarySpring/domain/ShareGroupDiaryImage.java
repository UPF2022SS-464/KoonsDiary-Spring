package UPF2022SS.KoonsDiarySpring.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "share_group_diary_image")
public class ShareGroupDiaryImage {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "share_group_diary_image")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "share_group_diary_id")
    private ShareGroupDiary shareGroupDiary;

    @Column(nullable = false)
    private String shareGroupDiaryImagePath;

    private String shareGroupDiaryComment;
}
