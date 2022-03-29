package UPF2022SS4.KoonsDiarySpring.service;

import UPF2022SS4.KoonsDiarySpring.api.dto.DefaultResponse;
import UPF2022SS4.KoonsDiarySpring.common.ResponseMessage;
import UPF2022SS4.KoonsDiarySpring.common.StatusCode;
import UPF2022SS4.KoonsDiarySpring.domain.User;
import UPF2022SS4.KoonsDiarySpring.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
                String pwd = user.getPassword();
                
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
}
