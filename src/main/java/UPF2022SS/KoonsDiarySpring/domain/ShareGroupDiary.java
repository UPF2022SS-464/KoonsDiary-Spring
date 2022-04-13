package UPF2022SS.KoonsDiarySpring.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
@Table(name = "share_group_diary")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShareGroupDiary {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "share_group_diary_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "share_group_id")
    private ShareGroup shareGroup;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String content;

    private LocalDateTime writeDate;
    private LocalDateTime editionData;

    private String thumbnailPath;

    @OneToMany(mappedBy = "shareGroupDiary", cascade = CascadeType.ALL)
    private List<ShareGroupDiaryImage> shareGroupDiaryImages = new ArrayList<>();

    @OneToMany(mappedBy = "shareGroupDiary", cascade = CascadeType.ALL)
    private List<ShareGroupDiaryComment> shareGroupDiaryComments = new ArrayList<>();

}
