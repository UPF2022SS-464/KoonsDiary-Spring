package UPF2022SS.KoonsDiarySpring.api.controller;

import UPF2022SS.KoonsDiarySpring.api.dto.shareGroup.ShareGroup.CreateRequest;
import UPF2022SS.KoonsDiarySpring.domain.User;
import UPF2022SS.KoonsDiarySpring.service.JwtService;
import UPF2022SS.KoonsDiarySpring.service.diary.sub.S3Service;
import UPF2022SS.KoonsDiarySpring.service.shareGroup.ShareGroupService;
import UPF2022SS.KoonsDiarySpring.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static UPF2022SS.KoonsDiarySpring.api.dto.shareGroup.ShareGroup.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ShareGroupApiController {

    private final UserService userService;
    private final JwtService jwtService;
    private final S3Service s3Service;
    private final ShareGroupService shareGroupService;

    // 공유일기장 생성 api
    @PostMapping(value ="/shareGroup")
    public ResponseEntity<String> createShareGroup(
            @RequestHeader("Authorization") final String header,
            @ModelAttribute
            final CreateRequest request
    ){
        User user = userService.findById(jwtService.decodeAccessToken(header));
        String shareGroupImagePath = s3Service.uploadFile(request.getShareGroupImage(), user);
        shareGroupService.createShareGroup(user, request.getShareGroupName(), shareGroupImagePath);

        return ResponseEntity.status(HttpStatus.CREATED).body(HttpStatus.CREATED.toString());
    }

    // 공유일기장 수정 api
    @PatchMapping(value = "/shareGroup")
    public ResponseEntity<String> patchShareGroup(
            @RequestHeader("Authorization") final String header,
            @ModelAttribute
            final PatchRequest request
            ){

        User user = userService.findById(jwtService.decodeAccessToken(header));

        String shareGroupImagePath = s3Service.uploadFile(request.getShareGroupImage(),user);
        UPF2022SS.KoonsDiarySpring.domain.ShareGroup shareGroup = shareGroupService.updateShareGroup(request, shareGroupImagePath);
        return ResponseEntity.ok().body(HttpStatus.OK.toString());
    }

    // 공유일기장 삭제 api
    @DeleteMapping(value = "/shareGroup")
    public ResponseEntity<String> deleteShareGroup(
            @RequestHeader("Authorization") final String header,
            @RequestBody final DeleteRequest request
            ){
        User user = userService.findById(jwtService.decodeAccessToken(header));
        Boolean result = shareGroupService.deleteShareGroup(request.getShareGroupId(), user);
        // 권한 확인 후 결과 반환
        if(result){
            return ResponseEntity.accepted().body(HttpStatus.ACCEPTED.toString());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(HttpStatus.UNAUTHORIZED.toString());
    }
}
