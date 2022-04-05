package UPF2022SS4.KoonsDiarySpring.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
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
    private int emotion;

    @OneToMany(mappedBy = "diary", cascade = CascadeType.ALL)
    private List<DiaryImage> diaryImageList = new ArrayList<>();

    @Column(nullable = false)
    private String thumbnailPath;

    public void setUser(User user){
        this.user = user;
    }

}
