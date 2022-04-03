package UPF2022SS4.KoonsDiarySpring.domain;

import lombok.*;

import javax.persistence.*;
import java.util.*;

@Table(name = "user")
@Entity
@Getter
@Builder
@AllArgsConstructor
@ToString
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id",nullable = false)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String nickname;

    private Long kakaoKey;

    @OneToOne(mappedBy = "user", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private RefreshToken refreshToken;


    private String fcmToken;

    @OneToMany(mappedBy = "user")
    private List<Diary> diarys = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<GroupUser> groupUsers = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<GroupDiaryComment> groupDiaryComments = new ArrayList<>();

    public User(){}

    // 재발급받은 리프레시 토큰 재저장
    public void setRefreshToken(RefreshToken refreshToken) {
        this.refreshToken = refreshToken;
    }


    // 사용자 닉네임 변경
    public void updateNickname(String nickname){
        this.nickname = nickname;
    }

}
