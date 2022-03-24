package UPF2022SS4.KoonsDiarySpring.common;

public class ResponseMessage {

    //User
    public static final String USER_CREATE_SUCCESS  = "회원 등록 성공";
    public static final String USER_CREATE_FAIL     = "회원 등록 실패";
    public static final String USER_DELETE_SUCCESS  = "회원 삭제 성공";
    public static final String USER_DELETE_FAIL     = "회원 삭제 실패";
    public static final String USER_SEARCH_SUCCESS  = "회원 조회 성공";
    public static final String USER_SEARCH_FAIL     = "회원 조회 실패";
    public static final String NOT_FOUND_USER       = "회원을 찾을 수 없습니다.";
    public static final String INVALID_USER         = "존재하지 않는 회원입니다.";
    public static final String DUPLICATED_USER      = "이미 등록된 회원입니다.";
    public static final String DUPLICATED_EMAIL     = "이미 등록된 이메일입니다.";

    public static final String LOGIN_SUCCESS        = "로그인 성공";
    public static final String LOGIN_FAIL           = "존재하지 않는 유저입니다.";

    // etc
    public static final String DB_ERROR                    = "디비 에러";
    public static final String INTERNAL_SERVER_ERROR       = "서버 내부 에러";
    public static final String NOT_CONTENT                 = "입력 정보가 부족합니다.";
    public static final String BAD_REQUEST                 = "잘못된 요청입니다.";
    public static final String UNAUTHORIZED                = "인증 실패.";
    public static final String AUTHORIZED                  = "인증 성공.";
    public static final String FOUND_TERMS                 = "약관 조회 성공.";
    public static final String NOT_FOUND_TERMS             = "약관 조회 실패.";

    public static final String FOUND_NOTICE                 = "공지사항 조회 성공.";
    public static final String NOT_FOUND_NOTICE             = "공지사 조회 실패.";
    public static final String SUCCESS_QUESTION             = "문의하기 성공 실패.";
}
