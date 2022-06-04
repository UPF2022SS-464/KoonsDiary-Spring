package UPF2022SS.KoonsDiarySpring.domain;

import lombok.*;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Table(name = "share_group")
@Getter @Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class ShareGroup {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "share_group_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "admin_id", nullable = false)
    private User user;

    private String shareGroupName;

    @Column(nullable = false)
    private String shareGroupImagePath;

    @Builder.Default
    @OneToMany(mappedBy = "shareGroup", cascade = CascadeType.ALL)
    private List<ShareGroupUser> shareGroupUsers = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "shareGroup",
            targetEntity = ShareGroupInvite.class, cascade = CascadeType.ALL)
    private List<ShareGroupInvite> shareGroupInvites = new ArrayList<>();

    public void updateAdmin(User user){
        this.user = user;
    }
    public void updateShareGroupName(String shareGroupName){
        this.shareGroupName = shareGroupName;
    }
    public  void updateShareImagePath(String shareGroupImagePath){
        this.shareGroupImagePath = shareGroupImagePath;
    }
}
