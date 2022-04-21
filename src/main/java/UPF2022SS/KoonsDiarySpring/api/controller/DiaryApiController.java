package UPF2022SS.KoonsDiarySpring.api.controller;

import UPF2022SS.KoonsDiarySpring.api.dto.diary.PostDiaryRequest;
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

            List<String> fileUrls = new ArrayList<String>();

            for (MultipartFile file : files) {
                String fileUrl = uploadService.uploadFile(file, header);
                fileUrls.add(fileUrl);
            }

            DefaultResponse response =  diaryService.postDiary(postDiaryRequest, header, fileUrls);
            return response;

        } catch (Exception e){
            log.error(e.getMessage());
            return DefaultResponse.response(
                    StatusCode.INTERNAL_SERVER_ERROR,
                    ResponseMessage.DIARY_POST_FAIL
            );
        }
    }

}
