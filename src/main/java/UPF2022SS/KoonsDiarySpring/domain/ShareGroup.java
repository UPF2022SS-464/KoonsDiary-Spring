package UPF2022SS.KoonsDiarySpring.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Table(name = "share_group")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ShareGroup {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "share_group_id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String admin;

    @Column(nullable = false)
    private String groupImagePath;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "shareGroup")
    private List<ShareGroupUser> shareGroupUsers = new ArrayList<>();

    @OneToMany(mappedBy = "shareGroup",
            targetEntity = ShareGroupInvite.class)
    private List<ShareGroupInvite> shareGroupInvites = new ArrayList<>();
}
