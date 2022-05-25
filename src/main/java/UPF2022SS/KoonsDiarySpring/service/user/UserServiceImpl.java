package UPF2022SS.KoonsDiarySpring.service.user;

import UPF2022SS.KoonsDiarySpring.api.dto.user.ContainedUserRequest;
import UPF2022SS.KoonsDiarySpring.api.dto.user.ContainedUserResponse;

import UPF2022SS.KoonsDiarySpring.api.dto.user.UpdateUser;
import UPF2022SS.KoonsDiarySpring.common.StatusCode;
import UPF2022SS.KoonsDiarySpring.domain.ImagePath;
import UPF2022SS.KoonsDiarySpring.repository.user.UserJpaRepository;
import UPF2022SS.KoonsDiarySpring.api.dto.DefaultResponse;
import UPF2022SS.KoonsDiarySpring.common.ResponseMessage;
import UPF2022SS.KoonsDiarySpring.domain.User;

import UPF2022SS.KoonsDiarySpring.service.image.ImageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static UPF2022SS.KoonsDiarySpring.api.dto.user.Kakao.*;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor()
public class UserServiceImpl implements UserService{

    private final UserJpaRepository userJpaRepository;
    private final ImageService imageService;

    @Override
    public ResponseEntity<String> join(User user) throws RuntimeException{
        try {
            // 아이디와 이메일에 대한 유효성 검사
            if (!validateDuplicateUserId(user.getUsername())) {
                return ResponseEntity
                        .status(409)
                        .body(ResponseMessage.DUPLICATED_USER);

            } else if (!validateDuplicateUserEmail(user.getEmail())) {
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
    public ResponseEntity<AccessDto> getKakaoId(String accessToken){
        try{
        Long kakaoId = getKakaoToken(accessToken);

            //아이디와 이메일에 대한 유효성 검사
            if (validateDuplicateKakaoId(kakaoId)){
                AccessDto accessDto = new AccessDto(kakaoId);
                return ResponseEntity.ok().body(accessDto);
            }
            return ResponseEntity.badRequest().build();
        }
        catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    public Long getKakaoToken(String accessToken) throws RuntimeException, IOException {

        String reqURL = "https://kapi.kakao.com/v2/user/me";

        //access_token을 이용하여 사용자 정보 조회
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", "Bearer " + accessToken); //전송할 header 작성, access_token전송

            //결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            //Gson 라이브러리로 JSON파싱
            JsonArray array = new Gson().fromJson(result, JsonArray.class);

        return array.getAsJsonObject().get("id").getAsLong();
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

    //카카오 아이디 중복 검사
    public boolean validateDuplicateKakaoId(Long kakaoId){
        Optional<User> findUser = userJpaRepository.findByKakaoId(kakaoId);
        return findUser.isEmpty();
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
    public User findUserKakaoId(Long kakaoId) {
        return userJpaRepository.findByKakaoId(kakaoId).get();
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
