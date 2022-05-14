package UPF2022SS.KoonsDiarySpring.service.user;

import UPF2022SS.KoonsDiarySpring.api.dto.user.ContainedUserRequest;
import UPF2022SS.KoonsDiarySpring.api.dto.user.ContainedUserResponse;
import UPF2022SS.KoonsDiarySpring.api.dto.user.SignUpRequest;
import UPF2022SS.KoonsDiarySpring.domain.RefreshToken;
import UPF2022SS.KoonsDiarySpring.repository.user.UserJpaRepository;
import UPF2022SS.KoonsDiarySpring.api.dto.DefaultResponse;
import UPF2022SS.KoonsDiarySpring.common.ResponseMessage;
import UPF2022SS.KoonsDiarySpring.common.StatusCode;
import UPF2022SS.KoonsDiarySpring.domain.User;
import UPF2022SS.KoonsDiarySpring.service.AuthService;
import UPF2022SS.KoonsDiarySpring.service.RefreshTokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
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
                //아이디와 이메일에 대한 유효성 검사
                if (validateDuplicateUserId(user.getUsername())){
                    return DefaultResponse.builder()
                            .status(StatusCode.CONFLICT)
                            .message(ResponseMessage.DUPLICATED_USER)
                            .build();
                }
                //카카오로그인할때는 여기가 걸린다. 이부분 고칠것
                else if(validateDuplicateUserEmail(user.getEmail())){
                    return DefaultResponse.builder()
                            .status(StatusCode.CONFLICT)
                            .message(ResponseMessage.DUPLICATED_EMAIL)
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
    public boolean validateDuplicateUserId(String userId){
        User findUser = userJpaRepository.findByName(userId);
        if (findUser == null){
            return false;
        }
        return true;
    }

    //이메일을 통한 검사
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
    public void updateUser(User user, String newNickname){
        user.updateNickname(newNickname);
    }

    @Override
    @Transactional
    public void deleteUser(Long id){
        userJpaRepository.deleteById(id);
    }

//    @Override
//    public DefaultResponse login(LoginRequest loginRequest) {
//        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getUserId(), loginRequest.getPassword());
//
//        String id = authenticationToken.getName();
//
//        Authentication authentication = AuthenticationManagerBuilder.;
//        return null;
//    }
}
