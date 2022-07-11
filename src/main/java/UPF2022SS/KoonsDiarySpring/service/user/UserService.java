package UPF2022SS.KoonsDiarySpring.service.user;

import UPF2022SS.KoonsDiarySpring.api.dto.user.*;
import UPF2022SS.KoonsDiarySpring.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    public List<User> findUsers();
    public User findById(Long id);
    public User findUsername(String username);
    public User findUserEmail(String email);

    public KakaoDto.Create.ResponseDto createKakao(KakaoDto.Create.RequestDto requestDto);
    public KakaoDto.Read.ResponseDto readKako(String token);


    public ContainedUserResponse findByContainedUser(ContainedUserRequest cur);

    public UserDto.Create.ResponseDto create(UserDto.Create.RequestDto requestDto);
    public UserDto.Read.ResponseDto readV1(UserDto.Read.RequestDto requestDto);
    public UserDto.Update.ResponseDto update(String token, UserDto.Update.RequestDto requestDto);
    public UserDto.Delete.ResponseDto delete(String token);
    public UserDto.Read.ResponseDtoV2 read(String token);
    public UserDto.Search.ResponseDto findByContainedUser(String nickname);

    public boolean validateDuplicateUserId(String userId);
    public boolean validateDuplicateUserEmail(String userEmail);




}
