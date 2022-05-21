package UPF2022SS.KoonsDiarySpring.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Getter @Setter
@Table(name = "share_group_member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ShareGroupUser {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "share_group_member_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "share_group_id")
    private ShareGroup shareGroup;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String status;

}
