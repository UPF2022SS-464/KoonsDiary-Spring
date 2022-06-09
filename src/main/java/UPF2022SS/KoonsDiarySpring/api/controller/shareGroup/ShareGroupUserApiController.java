package UPF2022SS.KoonsDiarySpring.api.controller.shareGroup;

import UPF2022SS.KoonsDiarySpring.api.dto.shareGroup.user.ShareGroupUser.*;
import UPF2022SS.KoonsDiarySpring.domain.Enum.Authority;
import UPF2022SS.KoonsDiarySpring.domain.ShareGroup;
import UPF2022SS.KoonsDiarySpring.domain.ShareGroupUser;
import UPF2022SS.KoonsDiarySpring.service.JwtService;
import UPF2022SS.KoonsDiarySpring.service.shareGroup.Invite.ShareGroupInviteService;
import UPF2022SS.KoonsDiarySpring.service.shareGroup.ShareGroupService;
import UPF2022SS.KoonsDiarySpring.service.shareGroup.user.ShareGroupUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ShareGroupUserApiController {

    private final ShareGroupInviteService shareGroupInviteService;
    private final JwtService jwtService;
    private final ShareGroupUserService shareGroupUserService;
    private final ShareGroupService shareGroupService;

    @PostMapping(value = "/shareGroupUser")
    public ResponseEntity<String> createShareGroupUser(
            @RequestHeader("Authorization") final String header,
            @RequestBody final createRequest request
            ){

        ShareGroupUser shareGroupUser = shareGroupUserService.createShareGroupUser(request.getShareGroupInviteId());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(HttpStatus.CREATED.toString());
    }

    // 공유일기 유저의 정보를 가져오는 api
    @GetMapping(value= "/shareGroupUser")
    public ResponseEntity<getResponse> getShareGroupUser(
            @RequestHeader("Authorization") final String header,
            @RequestBody final getRequest request
    ){

        Long userId = jwtService.decodeAccessToken(header);
        ShareGroup shareGroup = shareGroupService.getShareGroupV1(request.getShareGroupId());

        ShareGroupUser shareGroupUser = shareGroupUserService.getShareGroupUser(userId, request.getShareGroupId());

        getResponse response = getResponse.builder()
                .shareGroupId(shareGroupUser.getShareGroup().getId())
                .shareGroupUserId(shareGroupUser.getId())
                .userId(shareGroupUser.getUser().getId())
                .userNickname(shareGroupUser.getUser().getNickname())
                .userImagePath(shareGroupUser.getUser().getImagePath().getPath())
                .fcmToken(shareGroupUser.getUser().getFcmToken())
                .build();

        return ResponseEntity.ok().body(response);
    }

    //전체 유저 리스트를 가져오는 api
    @GetMapping(value= "/shareGroupUsers")
    public ResponseEntity<List<getListResponse>> getShareGroupUsers(
            @RequestHeader("Authorization") final String header,
            @RequestBody final getRequest request
    ){
        Long userId = jwtService.decodeAccessToken(header);

        List<ShareGroupUser> shareGroupUsers = shareGroupUserService.getShareGroupUsers(request.getShareGroupId());

        List<getListResponse> getListResponses = new ArrayList<getListResponse>();
        for (ShareGroupUser shareGroupUser : shareGroupUsers) {
            getListResponse response = getListResponse.builder()
                    .shareGroupUserId(shareGroupUser.getId())
                    .userId(shareGroupUser.getUser().getId())
                    .userNickname(shareGroupUser.getUser().getNickname())
                    .userImagePath(shareGroupUser.getUser().getImagePath().getPath())
                    .fcmToken(shareGroupUser.getUser().getFcmToken())
                    .build();

            getListResponses.add(response);
        }

        return ResponseEntity.ok().body(getListResponses);
    }


    //방장의 권한으로 유저 추방시킬 때 api
    @DeleteMapping(value = "/shareGroupUser")
    public ResponseEntity<String> expelShareGroupUser(
            @RequestHeader("Authorization") final String header,
            @RequestBody final deleteRequest request
    ){
        Long userId = jwtService.decodeAccessToken(header);

        shareGroupUserService.expelShareGroupUser(request.getShareGroupUserId());
        return ResponseEntity.ok().body(HttpStatus.OK.toString());
    }
}
