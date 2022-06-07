package UPF2022SS.KoonsDiarySpring.repository.shareGroup.invite;

import UPF2022SS.KoonsDiarySpring.domain.Enum.InvitationStatus;
import UPF2022SS.KoonsDiarySpring.domain.QShareGroupInvite;
import UPF2022SS.KoonsDiarySpring.domain.QUser;
import UPF2022SS.KoonsDiarySpring.domain.ShareGroupInvite;
import UPF2022SS.KoonsDiarySpring.domain.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ShareGroupInviteImpl implements ShareGroupInviteRepository{

    private final EntityManager em;

    private final JPAQueryFactory jqf;

    QShareGroupInvite qShareGroupInvite = QShareGroupInvite.shareGroupInvite;
    QUser qUser = QUser.user;

    /*
     * 회원들을 저장한다
     * 회원들의 fcm 토큰을 가져온다.
     * 회원들의 fcm 토큰에 메시지를 전달한다.
     * */

    //클라이언트에서 선택한 회원들의 fcm 토큰을 반환하는 메서드

    /*
    공유일기 초대를 수락하지 않은 유저를 확인하기 위한 메서드
    조건절에 그룹 아이디가 같으면서 상태가 보류상태인 회원의 닉네임을 반환
    */

    @Override
    public Optional<List<ShareGroupInvite>> findUserByShareGroupId(Long shareGroupId) {
        return Optional.of(jqf.select(qShareGroupInvite)
                .from(qShareGroupInvite)
                .where(qShareGroupInvite.shareGroup.id.eq(shareGroupId))
                .where(qShareGroupInvite.status.eq(InvitationStatus.HOLD))
                .fetch());
    }
}
