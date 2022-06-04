package UPF2022SS.KoonsDiarySpring.api.dto.shareGroup;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;

public class ShareGroup {

    @Data
    public static class CreateRequest {
        @NotEmpty
        String shareGroupName;
        MultipartFile shareGroupImage;
    }

    @Data
    public static class PatchRequest {
        @NotEmpty
        Long shareGroupId;
        Long userId;
        String shareGroupName;
        MultipartFile shareGroupImage;
    }

    @Data
    public static class DeleteRequest{
        @NotEmpty
        Long shareGroupId;
    }
}
