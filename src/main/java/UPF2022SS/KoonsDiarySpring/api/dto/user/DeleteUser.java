package UPF2022SS.KoonsDiarySpring.api.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


public class DeleteUser {

    @Data
    @AllArgsConstructor
    public static class Response{
        private String comment;
    }
}
