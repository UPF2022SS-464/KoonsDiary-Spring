package UPF2022SS.KoonsDiarySpring.Exception;

import UPF2022SS.KoonsDiarySpring.api.dto.DefaultResponse;
import UPF2022SS.KoonsDiarySpring.common.ResponseMessage;
import UPF2022SS.KoonsDiarySpring.common.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
@Slf4j
@RestController
public class ExceptionAdvisor {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> processValidationError(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        log.error(bindingResult.toString());
        return ResponseEntity
                .badRequest()
                .body(ResponseMessage.BAD_REQUEST);
    }

    // 없는값 참조시 예외
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String>  noSuchElementException(NoSuchElementException exception){
        log.error(exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(HttpStatus.NO_CONTENT.toString());
    }

    //null값에 대한 예외 처리
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> nullElementException(NullPointerException exception){
        log.error(exception.getMessage());
        return ResponseEntity
                .badRequest()
                .body(HttpStatus.BAD_REQUEST.toString());
    }

    //데이터 충돌에 대한 예외처리
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> divException(DataIntegrityViolationException exception){
        log.error(exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(HttpStatus.CONFLICT.toString());
    }
}

