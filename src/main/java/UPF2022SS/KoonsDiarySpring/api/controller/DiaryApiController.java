package UPF2022SS.KoonsDiarySpring.api.controller;

import UPF2022SS.KoonsDiarySpring.api.dto.diary.*;
import UPF2022SS.KoonsDiarySpring.domain.Diary;
import UPF2022SS.KoonsDiarySpring.domain.User;
import UPF2022SS.KoonsDiarySpring.service.JwtService;
import UPF2022SS.KoonsDiarySpring.service.diary.DiaryService;
import UPF2022SS.KoonsDiarySpring.api.dto.DefaultResponse;
import UPF2022SS.KoonsDiarySpring.common.ResponseMessage;
import UPF2022SS.KoonsDiarySpring.common.StatusCode;
import UPF2022SS.KoonsDiarySpring.service.diary.sub.UploadService;
import UPF2022SS.KoonsDiarySpring.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class DiaryApiController {

    @Autowired
    private DiaryService diaryService;
    @Autowired
    private UploadService uploadService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService userService;

    @PostMapping(value = "/diary")
    public DefaultResponse postDiary(
            @RequestHeader("Authorization") final String header,
            @RequestPart
            final PostDiaryRequest postDiaryRequest,
            @RequestPart(required = false)
            final List<MultipartFile> files) {
        try{
            if(header == null){
                return DefaultResponse.response(
                        StatusCode.UNAUTHORIZED,
                        ResponseMessage.UNAUTHORIZED
                );
            }

            //파일 이름이 들어갈 배열
            List<String> fileUrls = new ArrayList<String>();
            int cnt = 0;
            for (MultipartFile file : files) {
                cnt++;
                String fileUrl = uploadService.uploadFile(file, header);
                fileUrls.add(fileUrl+"_"+Integer.toString(cnt));
            }

            Long id = jwtService.decodeAccessToken(header);

            DefaultResponse response =  diaryService.postDiary(postDiaryRequest, id, fileUrls);
            return response;

        } catch (Exception e){
            log.error(e.getMessage());
            return DefaultResponse.response(
                    StatusCode.INTERNAL_SERVER_ERROR,
                    ResponseMessage.DIARY_POST_FAIL
            );
        }
    }

    /*
    * 다이어리 리스트 반환하는 API
    */
    @GetMapping(value = "/diarys")
    public DefaultResponse getDiaryList(
            @RequestHeader("Authorization") final String header
    ){

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
        DefaultResponse response = diaryService.getDiaryList(user);
        return response;
    }

    /*
     * 다이어리를 반환하는 API
     */
    @GetMapping(value = "/diary/{id}")
    public DefaultResponse getDiaryV1(
            @RequestHeader final String header, @PathVariable Long id
            ){

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
        DefaultResponse response = diaryService.getDiary(user, id);

        return response;
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
                //파일 이름이 들어갈 배열
                List<String> fileUrls = new ArrayList<String>();

                for (MultipartFile file : files) {
                    String fileUrl = uploadService.uploadFile(file, header);
                    fileUrls.add(fileUrl);
                }

//                Long id = jwtService.decodeAccessToken(header);

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
