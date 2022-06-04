package UPF2022SS.KoonsDiarySpring.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.*;

@Table(name = "user")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "refresh_token_id")
    private RefreshToken refreshToken;

    @OneToOne
    @JoinColumn(name = "image_id")
    private ImagePath imagePath;

    private Long kakaoId;

    private Long fcmToken;

    @Builder.Default
    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Diary> diaryList = new ArrayList<Diary>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ShareGroup> shareGroups = new ArrayList<ShareGroup>();

    @Builder.Default
    @OneToMany(mappedBy = "user")
    private List<ShareGroupUser> shareGroupUsers = new ArrayList<ShareGroupUser>();

    @Builder.Default
    @OneToMany(mappedBy = "user")
    private List<ShareGroupDiaryComment> shareGroupDiaryComments = new ArrayList<ShareGroupDiaryComment>();

    // 재발급받은 리프레시 토큰 재저장
    public void setRefreshToken(RefreshToken refreshToken) {
        this.refreshToken = refreshToken;
    }

    // 사용자 닉네임 변경
    public void updateNickname(String nickname){
        this.nickname = nickname;
    }

    public void updateImage(ImagePath imagePath){
        this.imagePath = imagePath;
    }

    public void updatePassword(String password){ this.password = password; }
}
