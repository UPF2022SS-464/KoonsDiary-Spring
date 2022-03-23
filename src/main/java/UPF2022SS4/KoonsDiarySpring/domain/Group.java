package UPF2022SS4.KoonsDiarySpring.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "group")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Group {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String admin;

    @Column(nullable = false)
    private String groupImagePath;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "group")
    private List<GroupUser> groupUsers = new ArrayList<>();

    @OneToMany(mappedBy = "group")
    private List<GroupInvite> groupInvites = new ArrayList<>();
}
