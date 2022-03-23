package UPF2022SS4.KoonsDiarySpring.domain;

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
@Table(name = "group_diary")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupDiary {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_diary_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String content;

    private LocalDateTime writeDate;
    private LocalDateTime editionData;

    private String thumbnailPath;

    @OneToMany(mappedBy = "groupDiary", cascade = CascadeType.ALL)
    private List<GroupDiaryImage> groupDiaryImages = new ArrayList<>();

    @OneToMany(mappedBy = "groupDiary", cascade = CascadeType.ALL)
    private List<GroupDiaryComment> groupDiaryComments = new ArrayList<>();

}
