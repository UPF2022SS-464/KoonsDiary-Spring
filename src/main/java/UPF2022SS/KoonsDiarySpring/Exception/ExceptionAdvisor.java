package UPF2022SS.KoonsDiarySpring.Exception;

import UPF2022SS.KoonsDiarySpring.api.dto.DefaultResponse;
import UPF2022SS.KoonsDiarySpring.common.ResponseMessage;
import UPF2022SS.KoonsDiarySpring.common.StatusCode;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class ExceptionAdvisor {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public DefaultResponse<String> processValidationError(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        return DefaultResponse.response(
                StatusCode.BAD_REQUEST,
                ResponseMessage.BAD_REQUEST,
                exception.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }
}