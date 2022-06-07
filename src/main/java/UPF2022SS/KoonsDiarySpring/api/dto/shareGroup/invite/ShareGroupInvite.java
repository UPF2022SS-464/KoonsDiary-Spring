package UPF2022SS.KoonsDiarySpring.api.dto.shareGroup.invite;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


public class ShareGroupInvite {

    @Data
    public static class createRequest{
        Long user;
        Long shareGroupId;
    }

    @Data
    public  static class getRequest{
        Long shareGroupId;
    }

    @Data
    public static class deleteRequest{
        Long shareGroupInviteId;
    }

    @Data
    @Builder
    @AllArgsConstructor
    public static class getResponse{
        Long shareGroupId;
        Long userId;
        String userNickname;
        String userImagePath;
        String fcmToken;
    }

    @Data
    @Builder
    @AllArgsConstructor
    public static class deleteResponse{
        Long shareGroupId;
        Long userId;
        String userNickname;
        String userImagePath;
        String fcmToken;
    }
}
