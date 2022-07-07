package UPF2022SS.KoonsDiarySpring.service.user;

import UPF2022SS.KoonsDiarySpring.api.dto.user.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    public List<UPF2022SS.KoonsDiarySpring.domain.User> findUsers();
    public UPF2022SS.KoonsDiarySpring.domain.User findById(Long id);
    public UPF2022SS.KoonsDiarySpring.domain.User findUsername(String username);
    public UPF2022SS.KoonsDiarySpring.domain.User findUserEmail(String email);
    public ContainedUserResponse findByContainedUser(ContainedUserRequest cur);


    public UserDto.Create.ResponseDto create(UserDto.Create.RequestDto requestDto);
    public UserDto.Read.ResponseDto readV1(UserDto.Read.RequestDto requestDto);
    public UserDto.Read.ResponseDto readV2(String token);
    public UserDto.Update.ResponseDto update(String token, UserDto.Update.RequestDto requestDto);
    public UserDto.Delete.ResponseDto delete(String token);

    public KakaoDto.Create.ResponseDto createKakao(KakaoDto.Create.RequestDto requestDto);
    public KakaoDto.Read.ResponseDto readKako(String token);

    public boolean validateDuplicateUserId(String userId);
    public boolean validateDuplicateUserEmail(String userEmail);

}
