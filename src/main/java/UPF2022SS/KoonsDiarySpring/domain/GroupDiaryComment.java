package UPF2022SS.KoonsDiarySpring.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
@Table(name = "group_diary_comment")
@NoArgsConstructor
public class GroupDiaryComment {

    @Id @GeneratedValue
    @Column(name = "group_diary_comment_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "group_diary_id",nullable = false)
    private GroupDiary groupDiary;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime writeDate;

    @Column(nullable = false)
    private LocalDateTime editionDate;

}
