package UPF2022SS.KoonsDiarySpring.domain;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Builder
@AllArgsConstructor
public class DiaryImage {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diary_image_id")
    private Long id;

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "diary_id")
    private Diary diary;

    @Column(nullable = false)
    private String image_path;

    private String comment;

    public void setDiary(Diary diary){
        this.diary = diary;
        diary.getDiaryImageList().add(this);
    }
}
