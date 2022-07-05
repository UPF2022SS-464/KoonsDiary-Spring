package UPF2022SS.KoonsDiarySpring.Exception.advice;

import UPF2022SS.KoonsDiarySpring.Exception.ApiResponse;
import UPF2022SS.KoonsDiarySpring.Exception.BadRequestException;
import UPF2022SS.KoonsDiarySpring.common.ResponseMessage;
import com.google.api.Http;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.NoSuchElementException;


@Slf4j
@RestController
@RestControllerAdvice
public class RestExceptionAdvice {

    //에러 로그 출력을 위한 메서드
    private void printCommonExceptionHandlerMessage(Exception e){
        log.warn(this.getClass() + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + "\n\t"
                + e.getClass() + ": " + e.getMessage() + "(" + e.getStackTrace()[0].toString() + ")");
    }

    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<Object> handleBadRequestException(BadRequestException e) {
        printCommonExceptionHandlerMessage(e);

        ApiResponse response = ApiResponse.error(HttpStatus.BAD_REQUEST, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    protected ResponseEntity<Object> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        printCommonExceptionHandlerMessage(e);

        ApiResponse response = ApiResponse.error(HttpStatus.BAD_REQUEST, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // valid관련 유효성 에러가 발생했을 경우
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> processValidationError(MethodArgumentNotValidException e) {
        printCommonExceptionHandlerMessage(e);

        ApiResponse response = ApiResponse.error(HttpStatus.BAD_REQUEST, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException e) {
        printCommonExceptionHandlerMessage(e);

        ApiResponse response = ApiResponse.error(HttpStatus.BAD_REQUEST, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IOException.class)
    protected ResponseEntity<Object> handleIOException(IOException e) {
        printCommonExceptionHandlerMessage(e);

        ApiResponse response = ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    // 없는값 참조시 예외
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Object>  noSuchElementException(NoSuchElementException e){
        printCommonExceptionHandlerMessage(e);

        ApiResponse response = ApiResponse.error(HttpStatus.NOT_FOUND, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


    // null값에 대한 예외 처리
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> nullElementException(EntityNotFoundException e){
        printCommonExceptionHandlerMessage(e);

        ApiResponse response = ApiResponse.error(HttpStatus.NO_CONTENT, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // 중복 데이터 관련 에러처리
    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<Object> EntityExistException(EntityExistsException e){
        printCommonExceptionHandlerMessage(e);

        ApiResponse response = ApiResponse.error(HttpStatus.CONFLICT, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
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

