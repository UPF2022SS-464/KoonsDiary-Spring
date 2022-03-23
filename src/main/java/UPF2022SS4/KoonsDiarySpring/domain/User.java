package UPF2022SS4.KoonsDiarySpring.domain;

import lombok.*;

import javax.persistence.*;
import java.util.*;

@Table(name = "user")
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id",nullable = false)
    private Long id;

    @Column(unique = true)
    private String userid;

    private String password;

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    private String nickname;

    private Long kakaoKey;

    private String salt;

    private String fcmToken;

    @OneToMany(mappedBy = "user")
    private List<Diary> diarys = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<GroupUser> groupUsers = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<GroupDiaryComment> groupDiaryComments = new ArrayList<>();
}
