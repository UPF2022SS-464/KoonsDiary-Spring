package UPF2022SS.KoonsDiarySpring.api.dto.shareGroup.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

public class ShareGroupUser {
    @Data
    public static class createRequest {
        Long shareGroupInviteId;
    }

    @Data
    public static class getRequest{
        Long shareGroupId;
    }

    @Data
    public static class deleteRequest{
        Long shareGroupUserId;
    }

    @Data
    @Builder
    @AllArgsConstructor
    public static class getResponse{
        Long shareGroupUserId;
        Long shareGroupId;
        Long userId;
        String userNickname;
        String userImagePath;
        String fcmToken;
    }

    @Data
    @Builder
    @AllArgsConstructor
    public static class getListResponse{
        Long shareGroupUserId;
        Long userId;
        String userNickname;
        String userImagePath;
        String fcmToken;
    }
}
