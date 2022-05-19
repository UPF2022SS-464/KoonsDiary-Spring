package UPF2022SS.KoonsDiarySpring.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
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

    @NotBlank
    @Column(unique = true, nullable = false)
    private String username;

    @NotBlank
    private String password;

    @Column(unique = true)
    @Email
    private String email;

    @Column(nullable = false)
    private String nickname;

    @OneToOne(mappedBy = "user", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private RefreshToken refreshToken;

    @JoinColumn(name = "image_id", nullable = false)
    private Image image;
    /*
     * 유저네임, 닉네임, 이미지 아이디
     * 카카오 로그인
     */

    private String fcmToken;

    @Builder.Default
    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Diary> diaryList = new ArrayList<Diary>();

    @Builder.Default
    @OneToMany(mappedBy = "user")
    private List<ShareGroupUser> shareGroupUsers = new ArrayList<ShareGroupUser>();

    @Builder.Default
    @OneToMany(mappedBy = "user")
    private List<ShareGroupDiaryComment> shareGroupDiaryComments = new ArrayList<ShareGroupDiaryComment>();

    public User(){}

    // 재발급받은 리프레시 토큰 재저장
    public void setRefreshToken(RefreshToken refreshToken) {
        this.refreshToken = refreshToken;
    }

    // 사용자 닉네임 변경
    public void updateNickname(String nickname){
        this.nickname = nickname;
    }

    public void updateImage(Image image){
        this.image = image;
    }

    public void updatePassword(String password){ this.password = password; }
}
