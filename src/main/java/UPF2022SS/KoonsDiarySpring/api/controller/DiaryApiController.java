package UPF2022SS.KoonsDiarySpring.api.controller;

import UPF2022SS.KoonsDiarySpring.api.dto.diary.*;
import UPF2022SS.KoonsDiarySpring.domain.User;
import UPF2022SS.KoonsDiarySpring.service.JwtService;
import UPF2022SS.KoonsDiarySpring.service.diary.DiaryService;
import UPF2022SS.KoonsDiarySpring.api.dto.DefaultResponse;
import UPF2022SS.KoonsDiarySpring.common.ResponseMessage;
import UPF2022SS.KoonsDiarySpring.common.StatusCode;
import UPF2022SS.KoonsDiarySpring.service.diary.sub.S3Service;
import UPF2022SS.KoonsDiarySpring.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class DiaryApiController {

    private final DiaryService diaryService;
    private final S3Service s3Service;
    private final JwtService jwtService;
    private final UserService userService;

    @PostMapping(value = "/diary")
    public ResponseEntity<Object> postDiary(
            @Validated
            @RequestHeader("Authorization") final String header,
            @ModelAttribute
            final PostDiary.Request request
           ) {
        try{
            if(header == null){
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(ResponseMessage.UNAUTHORIZED);
            }
            //Access 토큰을 통한 유저 조회
            User user = userService.findById(jwtService.decodeAccessToken(header));

            //user가 Null일 경우
            if(user== null){
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(ResponseMessage.UNAUTHORIZED);
            }

            //파일 이름이 들어갈 배열
            List<String> fileUrls = new ArrayList<String>();

            //iterate를 수행하여 배열에 파일 이름 저장
            for (MultipartFile file : request.getFiles()) {
                String fileUrl = s3Service.uploadFile(file, user);
                fileUrls.add(fileUrl);
            }

            return diaryService.postDiary(request, user, fileUrls);


        } catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ResponseMessage.BAD_REQUEST);
        }
    }

    /*
    * 다이어리 리스트 반환하는 API
    */
    @GetMapping(value = "/diarys")
    public ResponseEntity<Object> getDiaryList(
            @RequestHeader("Authorization") final String header
    ){
        if(header == null){
            ResponseEntity
                    .badRequest()
                    .body(ResponseMessage.BAD_REQUEST);
        }

        User user = (User)userService.findById(jwtService.decodeAccessToken(header));

        if(user == null){
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body(ResponseMessage.NOT_FOUND_USER);

        }
        return diaryService.getDiaryList(user);
    }

    /*
     * 해당 월에 대한 다이어리 리스트 반환하는 API
     */
    @GetMapping(value = "/diarys/{startDate}/{endDate}")
    public DefaultResponse getMonthlyDiaryList(
            @RequestHeader("Authorization") final String header,
            @PathVariable("startDate")final String start,
            @PathVariable("endDate")final String end
            ){
        if(header == null){
            return DefaultResponse
                    .response(
                            StatusCode.UNAUTHORIZED,
                            ResponseMessage.UNAUTHORIZED
                    );
        }
        // 유저
        User user = (User)userService.findById(jwtService.decodeAccessToken(header));
        if(user == null){
            return DefaultResponse
                    .response(
                            StatusCode.BAD_REQUEST,
                            ResponseMessage.NOT_FOUND_USER
                    );
        }
        try{
            LocalDate startDate = LocalDate.parse(start, DateTimeFormatter.ISO_DATE);
            LocalDate endDate = LocalDate.parse(end, DateTimeFormatter.ISO_DATE);

            return diaryService.getMonthlyDiaryListByLocalDate(user,startDate, endDate);
        }catch (Exception e){
            log.error(e.getMessage());
            return DefaultResponse.response(StatusCode.BAD_REQUEST, ResponseMessage.BAD_REQUEST);
        }
    }

    /*
     * 다이어리를 반환하는 API
     */
    @GetMapping(value = "/diary/{id}")
    public ResponseEntity<Object> getDiaryV1(
            @RequestHeader("Authorization") final String header, @PathVariable("id") Long id
            ){

        if(header == null){
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(ResponseMessage.UNAUTHORIZED);
        }
        User user = (User)userService.findById(jwtService.decodeAccessToken(header));
        if(user == null){
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body(ResponseMessage.NOT_FOUND_USER);
        }
        return diaryService.getDiary(user, id);
    }

    @GetMapping(value = "/diary/emotion/{start}/{end}")
    public DefaultResponse getEmotion(@RequestHeader final String header,
                                      @PathVariable("start") final String start,
                                      @PathVariable("end") final String end){
        if(header == null){
            return DefaultResponse
                    .response(
                            StatusCode.UNAUTHORIZED,
                            ResponseMessage.UNAUTHORIZED
                    );
        }
        User user = (User)userService.findById(jwtService.decodeAccessToken(header));
        if(user == null){
            return DefaultResponse
                    .response(
                            StatusCode.BAD_REQUEST,
                            ResponseMessage.NOT_FOUND_USER
                    );
        }
        try{

            LocalDate startDate = LocalDate.parse(start, DateTimeFormatter.ISO_DATE);
            LocalDate endDate = LocalDate.parse(end, DateTimeFormatter.ISO_DATE);

            DefaultResponse response = diaryService.getEmotionListByLocalDate(user, startDate, endDate);

            return response;
        }catch (Exception e){
            log.error(e.getMessage());
            return DefaultResponse.response(StatusCode.BAD_REQUEST, ResponseMessage.BAD_REQUEST);
        }
    }

    /*
     * 다이어리 업데이트 반환하는 API
    */
    @PatchMapping(value = "/diary")
    public DefaultResponse patchDiary(
            @RequestHeader final  String header,
            @RequestPart final PatchDiaryRequest request,
            @RequestPart final List<MultipartFile> files
            ) {

        if (header == null) {
            return DefaultResponse.response(
                    StatusCode.UNAUTHORIZED,
                    ResponseMessage.UNAUTHORIZED
            );
        }
            try {
                User user = userService.findById(jwtService.decodeAccessToken(header));
                //파일 이름이 들어갈 배열
                List<String> fileUrls = new ArrayList<String>();

                for (MultipartFile file : files) {
                    String fileUrl = s3Service.uploadFile(file, user);
                    fileUrls.add(fileUrl);
                }

                DefaultResponse response = diaryService.patchDiary(request, fileUrls);
                return response;
            } catch (Exception e) {
                log.error(e.getMessage());
                return DefaultResponse.response(
                        StatusCode.DB_ERROR,
                        ResponseMessage.DIARY_PATCH_FAIL,
                        e.getMessage());
            }
    }



    @DeleteMapping(value = "/diary")
    public DefaultResponse deleteDiary(
            @RequestHeader final String header,
            @RequestPart final DeleteDiaryRequest request
            ){
        try{
            if(header == null){
                return DefaultResponse.response(StatusCode.UNAUTHORIZED,
                        ResponseMessage.UNAUTHORIZED
                        );
            }
            User findUser = (User) userService.findById(jwtService.decodeAccessToken(header));

            if(findUser == null){
                return DefaultResponse.response(StatusCode.OK,
                        ResponseMessage.NOT_FOUND_USER);
            }

            DefaultResponse response =   diaryService.deleteDiary(request.getId());

            return response;
        }
        catch (Exception e){
            log.error(e.getMessage());
            return DefaultResponse.response(
                    StatusCode.INTERNAL_SERVER_ERROR,
                    ResponseMessage.INTERNAL_SERVER_ERROR,
                    e.getMessage()
            );
        }
    }
}
