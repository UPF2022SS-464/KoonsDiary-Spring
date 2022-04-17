package UPF2022SS.KoonsDiarySpring.service.user;

import UPF2022SS.KoonsDiarySpring.api.dto.user.ContainedUserRequest;
import UPF2022SS.KoonsDiarySpring.api.dto.user.ContainedUserResponse;
import UPF2022SS.KoonsDiarySpring.repository.user.UserJpaRepository;
import UPF2022SS.KoonsDiarySpring.api.dto.DefaultResponse;
import UPF2022SS.KoonsDiarySpring.common.ResponseMessage;
import UPF2022SS.KoonsDiarySpring.common.StatusCode;
import UPF2022SS.KoonsDiarySpring.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor()
public class UserServiceImpl implements UserService{

    private final UserJpaRepository userJpaRepository;

    @Override
    @Transactional
    public DefaultResponse join(User user) {
        try{
                if (validateDuplicateUserId(user)){
                    return DefaultResponse.builder()
                            .status(StatusCode.CONFLICT)
                            .message(ResponseMessage.DUPLICATED_USER)
                            .build();
                }
                else if(validateDuplicateUserEmail(user)){
                    return DefaultResponse.builder()
                            .status(StatusCode.CONFLICT)
                            .message(ResponseMessage.DUPLICATED_EMAIL)
                            .build();
                    }
            userJpaRepository.save(user);
            return DefaultResponse.builder()
                    .status(StatusCode.OK)
                    .message(ResponseMessage.USER_CREATE_SUCCESS)
                    .build();
        }
        catch (Exception e){
            return DefaultResponse.builder()
                    .status(StatusCode.DB_ERROR)
                    .message(ResponseMessage.DB_ERROR)
                    .build();
        }
    }

    //회원 중복 검사
    public boolean validateDuplicateUserId(User user){
        User findUser = userJpaRepository.findByName(user.getUsername());
        if (findUser == null){
            return false;
        }
        return true;
    }

    //이메일을 통한 검사
    public boolean validateDuplicateUserEmail(User user){
        User findUser = userJpaRepository.findByEmail(user.getEmail());
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
            String json = mapper.writeValueAsString(mapList);
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
    public void updateUser(User user, String newNickname){
        user.updateNickname(newNickname);
    }

    @Override
    @Transactional
    public void deleteUser(Long id){
        userJpaRepository.deleteById(id);
    }
}
