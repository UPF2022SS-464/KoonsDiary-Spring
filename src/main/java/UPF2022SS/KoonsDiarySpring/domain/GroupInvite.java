package UPF2022SS.KoonsDiarySpring.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
@Table(name = "group_invite")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupInvite {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_invite_id")
    private Long id;


    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "fcm_token")
    private User fcmToken;


}
