package UPF2022SS.KoonsDiarySpring.service.diary;

import UPF2022SS.KoonsDiarySpring.api.dto.diary.*;
import UPF2022SS.KoonsDiarySpring.domain.DiaryImage;
import UPF2022SS.KoonsDiarySpring.repository.diary.DiaryImageJpaRepository;
import UPF2022SS.KoonsDiarySpring.repository.diary.DiaryJpaRepository;
import UPF2022SS.KoonsDiarySpring.api.dto.DefaultResponse;
import UPF2022SS.KoonsDiarySpring.common.ResponseMessage;
import UPF2022SS.KoonsDiarySpring.common.StatusCode;
import UPF2022SS.KoonsDiarySpring.domain.Diary;
import UPF2022SS.KoonsDiarySpring.domain.User;
import UPF2022SS.KoonsDiarySpring.service.diary.sub.AnalyticService;
import UPF2022SS.KoonsDiarySpring.service.JwtService;
import UPF2022SS.KoonsDiarySpring.service.diary.sub.UploadService;
import UPF2022SS.KoonsDiarySpring.service.user.UserService;
import com.azure.ai.textanalytics.TextAnalyticsClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DiaryServiceImpl implements DiaryService{


    @Autowired
    private DiaryJpaRepository diaryJpaRepository;
    @Autowired
    private DiaryImageJpaRepository diaryImageJpaRepository;
    @Autowired
    private DiaryImageService diaryImageService;
    @Autowired
    private AnalyticService analyticService;


    @Autowired
    private UserService userService;
    @Autowired
    private UploadService uploadService;
    @Autowired
    private JwtService jwtService;

    @Override
    @Transactional
    public DefaultResponse postDiary(
            PostDiaryRequest postDiaryRequest,
            String header,
            List<String> files) {

        //DTO 체크
        if (postDiaryRequest == null){
            return DefaultResponse.response(
                    StatusCode.BAD_REQUEST,
                    ResponseMessage.DIARY_POST_FAIL
            );
        }

        //유저 정보 확인
        Long userId = jwtService.decodeAccessToken(header);
        User findUser = userService.findById(userId);

        //유저 유효성 검사
        if (findUser ==null){
            return DefaultResponse.response(
                    StatusCode.UNAUTHORIZED,
                    ResponseMessage.INVALID_USER
            );
        }

        // 텍스트 분석을 통한 감정 추출에 대한 설정 및 구현
        TextAnalyticsClient client = analyticService.authenticateClient();
        int emotion = analyticService.AnalysisSentiment(client,postDiaryRequest.getContent());


        try{
            //다이어리 이미지 세팅 전에 다이어리 객체 세팅
            Diary diary = Diary.builder()
                            .user(findUser)
                            .writeDate(postDiaryRequest.getWriteDate())
                            .editionDate(postDiaryRequest.getEditionDate())
                            .content(postDiaryRequest.getContent())
                            .emotion(emotion)
                            .thumbnailPath(files.get(0)) //-> image path의 첫번째 값을 가져온다.
                            .build();

            diaryJpaRepository.save(diary);

            List<DiaryImage> diaryImageList = new ArrayList<>();

            //image path와 comment에 대한 반복문을 위해 지정
            Iterator<String> fileIterator = files.iterator();
            Iterator<String> commentIterator = postDiaryRequest.getComment().iterator();

            // 반복문을 통한 이미지 저장 밋 배열 내 데이터 추가
            while (fileIterator.hasNext() && commentIterator.hasNext()) {
                DiaryImage diaryImage = diaryImageService.saveImage(
                        fileIterator.next(),
                        commentIterator.next());

                diaryImageList.add(diaryImage);
            }

            diary.setDiaryImageList(diaryImageList);

            PostDiaryResponse response = new PostDiaryResponse(diary.getId(),
                    diary.getUser(),
                    diary.getWriteDate(),
                    diary.getEditionDate(),
                    diary.getContent(),
                    diary.getEmotion());

            return DefaultResponse.response(
                    StatusCode.OK,
                    ResponseMessage.DIARY_POST_SUCCESS,
                    response
                );

        } catch (Exception e){
            log.error(e.getMessage());
            return DefaultResponse.response(
                    StatusCode.INTERNAL_SERVER_ERROR,
                    ResponseMessage.DIARY_POST_FAIL
            );
        }
    }

    @Override
    public DefaultResponse getDiary(GetDiaryRequest getDiaryRequest) {
        try{
            String token = getDiaryRequest.getAccessToken();

            Long userId = jwtService.decodeAccessToken(token);

            User user = userService.findById(userId);
            Optional<Diary> diary = diaryJpaRepository.findById(getDiaryRequest.getId());

            System.out.println("diary = " + diary.get().getUser().getId() + diary.get().getUser().getNickname());

            GetDiaryResponse response = GetDiaryResponse.builder()
                    .id(diary.get().getId())
                    .user(diary.get().getUser())
                    .writeDate(diary.get().getWriteDate())
                    .editionDate(diary.get().getEditionDate())
                    .content(diary.get().getContent())
                    .emotion(diary.get().getEmotion())
                    .diaryImageList(diary.orElseThrow().getDiaryImageList())
                    .build();

            return DefaultResponse.response(
                    StatusCode.OK,
                    ResponseMessage.DIARY_GET_SUCCESS,
                    response
            );
        }
        catch (Exception e){
            log.error(e.getMessage());
            return DefaultResponse.response(
                    StatusCode.INTERNAL_SERVER_ERROR,
                    ResponseMessage.DIARY_GET_FAIL
            );
        }
    }

    @Override
    public DefaultResponse getDiaryList(GetDiaryListRequest getDiaryListRequest) {

        Long userId = jwtService.decodeAccessToken(getDiaryListRequest.getAccessToken());
        User findUser = userService.findById(userId);

        if(findUser == null){
            return DefaultResponse.response(
                    StatusCode.BAD_REQUEST,
                    ResponseMessage.USER_SEARCH_FAIL
            );
        }

        try{

            List<Diary> diaryList = diaryJpaRepository.findAllById(findUser.getId());

            GetDiaryListResponse diaryListResponse = GetDiaryListResponse
                    .builder()
                    .diaryList(diaryList)
                    .build();

            return DefaultResponse.response(
                    StatusCode.OK,
                    ResponseMessage.DIARY_GET_SUCCESS,
                    diaryListResponse
            );

        }catch (Exception e){
            log.error(e.getMessage());
            return DefaultResponse.response(
                    StatusCode.INTERNAL_SERVER_ERROR,
                    ResponseMessage.DIARY_GET_FAIL,
                    e.getMessage()
            );
        }
    }

    // return diary
    @Override
    @Transactional
    public DefaultResponse patchDiary(Diary diary) {
        return null;
    }

    // return void value
    @Override
    @Transactional
    public DefaultResponse deleteDiary(Diary diary) {
        if(diary == null){
            return DefaultResponse.response(
                    StatusCode.BAD_REQUEST,
                    ResponseMessage.INVALID_DIARY
            );
        }
        try {
            diaryJpaRepository.delete(diary);
            return DefaultResponse.response(
                    StatusCode.OK,
                    ResponseMessage.DIARY_DELETE_SUCCESS
            );

        }catch (Exception e){
            return DefaultResponse.response(
                    StatusCode.INTERNAL_SERVER_ERROR,
                    ResponseMessage.Diary_DELETE_FAIL
            );
        }
    }

    private Boolean validateCheckDiary(Diary diary){

        return null;
    }
}
