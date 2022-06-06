package UPF2022SS.KoonsDiarySpring.domain;

import UPF2022SS.KoonsDiarySpring.domain.Enum.InvitationStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Getter @Setter
@Table(name = "share_group_invite")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ShareGroupInvite {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "share_group_invite_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "share_group_id")
    private ShareGroup shareGroup;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private InvitationStatus status;
}
