package UPF2022SS.KoonsDiarySpring.service.user;

import UPF2022SS.KoonsDiarySpring.Exception.BadRequestException;
import UPF2022SS.KoonsDiarySpring.Exception.CustomExceptionMessage;
import UPF2022SS.KoonsDiarySpring.api.dto.user.*;

import UPF2022SS.KoonsDiarySpring.domain.ImagePath;
import UPF2022SS.KoonsDiarySpring.domain.RefreshToken;
import UPF2022SS.KoonsDiarySpring.domain.User;
import UPF2022SS.KoonsDiarySpring.repository.user.UserJpaRepository;

import UPF2022SS.KoonsDiarySpring.service.JwtService;
import UPF2022SS.KoonsDiarySpring.service.RefreshTokenService;
import UPF2022SS.KoonsDiarySpring.service.image.ImageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor()
public class UserServiceImpl implements UserService{

    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final UserJpaRepository userJpaRepository;
    private final PasswordEncoder passwordEncoder;
    private final ImageService imageService;

    @Override
    @Transactional
    public KakaoDto.Create.ResponseDto createKakao(KakaoDto.Create.RequestDto requestDto) {
        ImagePath imagePath = imageService.findImage(requestDto.getImageId()).orElseThrow(EntityNotFoundException::new);
        User user = User.builder()
                .username(requestDto.getUserId())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .nickname(requestDto.getNickname())
                .kakaoId(requestDto.getKakaoId())
                .imagePath(imagePath)
                .build();

        userJpaRepository.save(user);
        String accessToken = jwtService.createAccessToken(user.getId());

        return KakaoDto.Create.ResponseDto.of(user, accessToken);
    }

    @Override
    public KakaoDto.Read.ResponseDto readKako(String token) {
        Long kakaoId = null;
        try {
            kakaoId = getKakaoToken(token);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //아이디와 이메일에 대한 유효성 검사
        KakaoDto.AccessDto accessDto = new KakaoDto.AccessDto(kakaoId);

        return KakaoDto.Read.ResponseDto.of(kakaoId);
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
        Optional<User> findUser = userJpaRepository.findByUsername(userId);
        return findUser.isEmpty();
    }

    //이메일 중복 검사
    @Override
    public boolean validateDuplicateUserEmail(String userEmail){
        Optional<User> findUser = userJpaRepository.findByEmail(userEmail);
       return findUser.isEmpty();
    }

    //카카오 아이디 중복 검사
    public boolean validateDuplicateKakaoId(Long kakaoId){
        Optional<User> findUser = userJpaRepository.findByKakaoId(kakaoId);
        return findUser.isEmpty();
    }

    @Override
    public User findById(Long id){
        User user = userJpaRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return user;
    }

    @Override
    public List<User> findUsers() {
        return userJpaRepository.findAll();
    }

    @Override
    public User findUsername(String username){
        return userJpaRepository.findByUsername(username).get();
    }

    @Override
    public User findUserEmail(String email){
        return userJpaRepository.findByEmail(email).get();
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
    public UserDto.Delete.ResponseDto delete(String token){
        User user = null;
        if(token != null){
            user = userJpaRepository.findById(jwtService.decodeAccessToken(token)).orElseThrow(EntityNotFoundException::new);
        }
        userJpaRepository.delete(user);
        return UserDto.Delete.ResponseDto.of();
    }

    @Override
    public UserDto.Read.ResponseDtoV2 read(String token) {
        User user = null;
        if(token != null)
            user = userJpaRepository.findById(jwtService.decodeAccessToken(token))
                    .orElseThrow(EntityNotFoundException::new);

        return UserDto.Read.ResponseDtoV2.of(user);
    }

    @Override
    public UserDto.Search.ResponseDto findByContainedUser(String nickname) {
        List<User> userList = userJpaRepository.findByContainedName(nickname);
        ObjectMapper mapper = new ObjectMapper();

        List<HashMap<String, String>> mapList = new ArrayList<HashMap<String, String>>();

        for (UPF2022SS.KoonsDiarySpring.domain.User user : userList) {
            HashMap<String, String> map= new HashMap<String, String>();
            map.put("id", user.getId().toString());
            map.put("nickname", user.getNickname());

            mapList.add(map);
        }
        String json = null;
        try {
            json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mapList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return UserDto.Search.ResponseDto.of(json);
    }

    @Override
    @Transactional
    public UserDto.Create.ResponseDto create(UserDto.Create.RequestDto requestDto) {
        if(!validateDuplicateUserId(requestDto.getUserId()) || !validateDuplicateUserEmail(requestDto.getEmail())){
            throw new EntityExistsException();
        }

        String password = passwordEncoder.encode(requestDto.getPassword());
        ImagePath imagePath = imageService.findImage(requestDto.getImageId()).orElseThrow(EntityNotFoundException::new);

        User user = requestDto.toEntity(password, imagePath);

        RefreshToken refreshToken = new RefreshToken(user, jwtService.createRefreshToken());
        refreshTokenService.save(refreshToken);
        user.setRefreshToken(refreshToken);

        user = userJpaRepository.save(user);

        String accessToken = jwtService.createAccessToken(user.getId());

        return UserDto.Create.ResponseDto.of(user, accessToken);
    }

    public UserDto.Read.ResponseDto readV1(UserDto.Read.RequestDto requestDto){
        User user = null;
        if (requestDto.getId().contains("@")){
            user = userJpaRepository.findByEmail(requestDto.getId())
                    .orElseThrow(()->new EntityNotFoundException(CustomExceptionMessage.DATA_NOT_FOUND_MESSAGE));;
        }
        else{
            user = userJpaRepository.findByUsername(requestDto.getId())
                    .orElseThrow(()->new EntityNotFoundException(CustomExceptionMessage.DATA_NOT_FOUND_MESSAGE));
        }

        if (!user.getPassword().equals(passwordEncoder.encode(requestDto.getPassword()))) throw new BadRequestException();

        return UserDto.Read.ResponseDto.of(user, jwtService.createAccessToken(user.getId()));
    }

    @Override
    @Transactional
    public UserDto.Update.ResponseDto update(String token, UserDto.Update.RequestDto requestDto){
        User user = null;
        if (token != null)
            user = userJpaRepository.findById(jwtService.decodeAccessToken(token)).orElseThrow(EntityNotFoundException::new);

        if(requestDto.getPassword() != null){
            String pw = passwordEncoder.encode(requestDto.getPassword());
            user.updatePassword(pw);
        }
        if(requestDto.getNickname() != null){
            user.updateNickname(requestDto.getNickname());
        }
        if(requestDto.getImageId() != null){
            Optional<ImagePath> findImage = imageService.findImage(requestDto.getImageId());
            user.updateImage(findImage.get());
        }
        userJpaRepository.save(user);

        return UserDto.Update.ResponseDto.of(user);
    }
}
