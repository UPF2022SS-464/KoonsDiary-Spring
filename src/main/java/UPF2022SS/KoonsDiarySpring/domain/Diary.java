package UPF2022SS.KoonsDiarySpring.domain;

import UPF2022SS.KoonsDiarySpring.domain.Enum.EmotionStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Table(name = "diary")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Diary {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diary_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @Column(nullable = false)
    private LocalDate writeDate;

    @Column(nullable = false)
    private LocalDateTime editionDate;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
//    @Enumerated(EnumType.STRING)
    private int emotion;

    @Builder.Default
    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL)
    private List<DiaryImage> diaryImageList = new ArrayList<DiaryImage>();

    @Column(nullable = false)
    private String thumbnailPath;

    public void setUser(User user){
        this.user = user;
        user.getDiaryList().add(this);
    }

    public void setDiaryImageList(List<DiaryImage> diaryImageList){
        this.diaryImageList = diaryImageList;
//        for (DiaryImage diaryImage : diaryImageList) {
//            diaryImage.setDiary(this);
//        }
    }
}
