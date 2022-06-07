package UPF2022SS.KoonsDiarySpring.api.controller;

import UPF2022SS.KoonsDiarySpring.api.dto.shareGroup.invite.ShareGroupInvite.*;
import UPF2022SS.KoonsDiarySpring.domain.ShareGroupInvite;
import UPF2022SS.KoonsDiarySpring.service.shareGroup.Invite.ShareGroupInviteService;
import UPF2022SS.KoonsDiarySpring.service.shareGroup.ShareGroupService;
import UPF2022SS.KoonsDiarySpring.service.shareGroup.firebase.FirebaseCloudMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ShareGroupInviteApiController {

    private final ShareGroupInviteService shareGroupInviteService;
    private final ShareGroupService shareGroupService;
    private final FirebaseCloudMessageService firebaseCloudMessageService;

    @PostMapping(value = "/shareGroupInvite")
    public ResponseEntity<String> createShareGroupInvite(
            @RequestHeader("Authorization") final String header,
            @RequestBody final createRequest request
            ) throws IOException {
        if(header == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(HttpStatus.UNAUTHORIZED.toString());
        }

        String fcmToken = shareGroupInviteService.createShareGroupInvites(request);
        String shareGroup = shareGroupService.getShareGroupV1(request.getShareGroupId()).getShareGroupName();

        String body = shareGroup + "에 초대되셨습니다.";
        firebaseCloudMessageService.sendMessageTo(fcmToken,"공유 일기장 초대 알림",body);

        return ResponseEntity.ok().body(HttpStatus.CREATED.toString());
    }

    @GetMapping(value = "/shareGroupInvite")
    public ResponseEntity<List<getResponse>> getShareGroupInvite(
            @RequestHeader("Authorization") final String header,
            @RequestBody final getRequest request
    ){
        List<ShareGroupInvite> shareGroupInvites = shareGroupInviteService.getShareGroupInvite(request);
        List<getResponse> responses = new ArrayList<>();

        for (ShareGroupInvite shareGroupInvite : shareGroupInvites) {
            getResponse response = getResponse.builder()
                    .shareGroupId(shareGroupInvite.getShareGroup().getId())
                    .userId(shareGroupInvite.getUser().getId())
                    .userImagePath(shareGroupInvite.getUser().getImagePath().getPath())
                    .userNickname(shareGroupInvite.getUser().getNickname())
                    .build();
            responses.add(response);
        }

        return ResponseEntity.ok().body(responses);
    }

    @DeleteMapping(value = "/shareGroupInvite")
    public ResponseEntity<List<deleteResponse>> DeleteShareGroupInvite(
            @RequestHeader("Authorization") final String header,
            @RequestBody final deleteRequest request
    ){
        List<ShareGroupInvite> shareGroupInvites = shareGroupInviteService.DeleteShareGroupInvite(request);
        List<deleteResponse> responses = new ArrayList<deleteResponse>();
        for (ShareGroupInvite shareGroupInvite : shareGroupInvites) {
            deleteResponse response = deleteResponse.builder()
                    .shareGroupId(shareGroupInvite.getShareGroup().getId())
                    .userId(shareGroupInvite.getUser().getId())
                    .userImagePath(shareGroupInvite.getUser().getImagePath().getPath())
                    .userNickname(shareGroupInvite.getUser().getNickname())
                    .build();
            responses.add(response);
        }

        return ResponseEntity.ok().body(responses);
    }
}
