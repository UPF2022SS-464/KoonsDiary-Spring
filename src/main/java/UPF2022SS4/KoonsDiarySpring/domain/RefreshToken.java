package UPF2022SS4.KoonsDiarySpring.domain;


import lombok.*;

import javax.persistence.*;



@Getter
@Table(name = "refresh_token")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refresh_token_id", nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @Column(nullable = false)
    private String value;

    public RefreshToken(final User user, final String value){
        this.user = user;
        this.value = value;
    }

    public  void addUser(final User user){
        this.user = user;
        user.setRefreshToken(this);
    }

    public void deleteUser(final User user) {
        this.user = null;
        user.setRefreshToken(null);
    }

    public void renewalRefreshToken(final String value){
        this.value = value;
    }
//    public RefreshToken(){} //-> 접근을 위한 일반 생성자 선언
}
