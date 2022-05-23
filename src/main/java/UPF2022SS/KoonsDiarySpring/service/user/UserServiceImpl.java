package UPF2022SS.KoonsDiarySpring.service.user;

import UPF2022SS.KoonsDiarySpring.api.dto.user.ContainedUserRequest;
import UPF2022SS.KoonsDiarySpring.api.dto.user.ContainedUserResponse;

import UPF2022SS.KoonsDiarySpring.api.dto.user.UpdateUser;
import UPF2022SS.KoonsDiarySpring.domain.ImagePath;
import UPF2022SS.KoonsDiarySpring.repository.user.UserJpaRepository;
import UPF2022SS.KoonsDiarySpring.api.dto.DefaultResponse;
import UPF2022SS.KoonsDiarySpring.common.ResponseMessage;
import UPF2022SS.KoonsDiarySpring.common.StatusCode;
import UPF2022SS.KoonsDiarySpring.domain.User;

import UPF2022SS.KoonsDiarySpring.service.image.ImageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor()
public class UserServiceImpl implements UserService{

    private final UserJpaRepository userJpaRepository;
    private final ImageService imageService;

    @Override
    @Transactional
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR,reason = "데이터베이스 에러")
    public ResponseEntity<String> join(User user) throws RuntimeException{
        try {
            // 아이디와 이메일에 대한 유효성 검사
            if (validateDuplicateUserId(user.getUsername())) {
                return ResponseEntity
                        .status(409)
                        .body(ResponseMessage.DUPLICATED_USER);

            } else if (validateDuplicateUserEmail(user.getEmail())) {
                return ResponseEntity
                        .status(409)
                        .body(ResponseMessage.DUPLICATED_EMAIL);
            }

            // 유저 정보 저장
            userJpaRepository.save(user);

            return ResponseEntity.ok()
                    .header(ResponseMessage.USER_CREATE_SUCCESS)
                    .build();
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity
                    .badRequest()
                    .body(ResponseMessage.BAD_REQUEST);
        }
    }

    @Override
    @Transactional
    public DefaultResponse kakaoJoin(User user) {
        try{
            //아이디와 이메일에 대한 유효성 검사
            if (validateDuplicateUserId(user.getUsername())){
                return DefaultResponse.builder()
                        .status(StatusCode.CONFLICT)
                        .message(ResponseMessage.DUPLICATED_USER)
                        .build();
            }
            //유저 정보 저장
            userJpaRepository.save(user);

            DefaultResponse response = DefaultResponse.builder()
                    .status(StatusCode.OK)
                    .message(ResponseMessage.USER_CREATE_SUCCESS)
                    .build();

            return response;
        }
        catch (Exception e){
            log.error(e.getMessage());
            return DefaultResponse.builder()
                    .status(StatusCode.DB_ERROR)
                    .message(ResponseMessage.DB_ERROR)
                    .data(e.getMessage())
                    .build();
        }
    }

    //회원 중복 검사
    @Override
    public boolean validateDuplicateUserId(String userId){
        User findUser = userJpaRepository.findByName(userId);
        if (findUser == null){
            return false;
        }
        return true;
    }

    //이메일을 통한 검사
    @Override
    public boolean validateDuplicateUserEmail(String userEmail){
        User findUser = userJpaRepository.findByEmail(userEmail);
        if (findUser == null){
            return false;
        }
        return true;
    }

    @Override
    public User findById(Long id){
        Optional<User> user = userJpaRepository.findById(id);
        return user.get();
    }

    @Override
    public List<User> findUsers() {
        return userJpaRepository.findAll();
    }

    @Override
    public User findUsername(String username){
        return userJpaRepository.findByName(username);
    }

    @Override
    public User findUserEmail(String email){
        return userJpaRepository.findByEmail(email);
    }

    @Override
    public ContainedUserResponse findByContainedUser(ContainedUserRequest cur) {
        List<User> userList = userJpaRepository.findByContainedName(cur.getNickname());

        ObjectMapper mapper = new ObjectMapper();

        List<HashMap<String, String>> mapList = new ArrayList<HashMap<String, String>>();

        for (User user : userList) {
            HashMap<String, String> map= new HashMap<String, String>();
            map.put("id", user.getId().toString());
            map.put("nickname", user.getNickname());

            mapList.add(map);
        }
        try {
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mapList);

            ContainedUserResponse containedUserResponse = ContainedUserResponse.builder()
                    .userListJsonData(json)
                    .build();
            return containedUserResponse;
        } catch (Exception e){
            return null;
        }
    }

    @Override
    @Transactional
    public void updateUser(User user, UpdateUser.Request request){
        if(request.getPassword() != null){
          user.updatePassword(request.getPassword());
        }
        if(request.getNickname() != null){
            user.updateNickname(request.getNickname());
        }
        if(request.getImageId() != null){
            Optional<ImagePath> findImage = imageService.findImage(request.getImageId());
            user.updateImage(findImage.get());
        }
    }

    @Override
    @Transactional
    public void deleteUser(Long id){
        userJpaRepository.deleteById(id);
    }
}
