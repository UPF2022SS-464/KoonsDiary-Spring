package UPF2022SS.KoonsDiarySpring.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class DefaultResponse<T> {
    private int status;
    private String message;
    private T data;


    public DefaultResponse(int status, String message) {
        this.status = status;
        this.message = message;
        this.data = null;
    }


    public static<T> DefaultResponse<T> response(final int status, final String message){
        return DefaultResponse.<T>builder()
                .status(status)
                .message(message)
                .build();
    }

    public static<T> DefaultResponse<T> response(final int status, final String message, final T data) {
        return DefaultResponse.<T>builder()
                .data(data)
                .status(status)
                .message(message)
                .build();
    }
}
