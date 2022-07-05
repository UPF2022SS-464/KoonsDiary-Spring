package UPF2022SS.KoonsDiarySpring.Exception;

public class BadRequestException extends RuntimeException{

    public BadRequestException() {
        super();
    }

    public BadRequestException(String s){
        super(s);
    }
}
