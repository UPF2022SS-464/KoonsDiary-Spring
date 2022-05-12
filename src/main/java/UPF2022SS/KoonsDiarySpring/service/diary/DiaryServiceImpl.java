package UPF2022SS.KoonsDiarySpring.service.diary;

import UPF2022SS.KoonsDiarySpring.api.dto.diary.*;
import UPF2022SS.KoonsDiarySpring.api.dto.user.ContainedUserResponse;
import UPF2022SS.KoonsDiarySpring.domain.DiaryImage;
import UPF2022SS.KoonsDiarySpring.repository.diary.DiaryImageJpaRepository;
import UPF2022SS.KoonsDiarySpring.repository.diary.DiaryJpaRepository;
import UPF2022SS.KoonsDiarySpring.api.dto.DefaultResponse;
import UPF2022SS.KoonsDiarySpring.common.ResponseMessage;
import UPF2022SS.KoonsDiarySpring.common.StatusCode;
import UPF2022SS.KoonsDiarySpring.domain.Diary;
import UPF2022SS.KoonsDiarySpring.domain.User;
import UPF2022SS.KoonsDiarySpring.repository.user.UserJpaRepository;
import UPF2022SS.KoonsDiarySpring.service.diary.sub.AnalyticService;
import UPF2022SS.KoonsDiarySpring.service.JwtService;
import UPF2022SS.KoonsDiarySpring.service.diary.sub.UploadService;
import UPF2022SS.KoonsDiarySpring.service.user.UserService;
import com.azure.ai.textanalytics.TextAnalyticsClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

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
    private UserJpaRepository userJpaRepository;
    @Autowired
    private UploadService uploadService;
    @Autowired
    private JwtService jwtService;

    @Override
    @Transactional
    public DefaultResponse postDiary(
            PostDiaryRequest postDiaryRequest,
            Long userId,
            List<String> files) {

        //DTO 체크
        if (postDiaryRequest == null){
            return DefaultResponse.response(
                    StatusCode.BAD_REQUEST,
                    ResponseMessage.DIARY_POST_FAIL
            );
        }
        Optional<User> user = userJpaRepository.findById(userId);

        //유저 유효성 검사
        if (user == null){
            return DefaultResponse.response(
                    StatusCode.UNAUTHORIZED,
                    ResponseMessage.INVALID_USER
            );
        }

        // 텍스트 분석을 통한 감정 추출에 대한 설정 및 구현
        TextAnalyticsClient client = analyticService.authenticateClient();
        int emotion = analyticService.AnalysisSentiment(client,postDiaryRequest.getContent());

        List<DiaryImage> diaryImageList = new ArrayList<>();

        try{
            //다이어리 이미지 세팅 전에 다이어리 객체 세팅
            Diary diary = Diary.builder()
                            .user(user.get())
                            .writeDate(postDiaryRequest.getWriteDate())
                            .editionDate(postDiaryRequest.getEditionDate())
                            .content(postDiaryRequest.getContent())
                            .emotion(emotion)
                            .thumbnailPath(files.get(0)) //-> image path의 첫번째 값을 가져온다.
                            .build();

            diaryJpaRepository.save(diary);

            //image path와 comment에 대한 반복문을 위해 지정
            Iterator<String> fileIterator = files.iterator();
            Iterator<String> commentIterator = postDiaryRequest.getComment().iterator();

            // 반복문을 통한 이미지 저장 밋 배열 내 데이터 추가
            while (fileIterator.hasNext() && commentIterator.hasNext()) {

                DiaryImage diaryImage = DiaryImage
                        .builder()
                        .image_path(fileIterator.next())
                        .comment(commentIterator.next())
                        .diary(diary)
                        .build();

                diaryImageJpaRepository.save(diaryImage);

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
                    ResponseMessage.DIARY_POST_FAIL,
                    e.getMessage()
            );
        }
    }

    @Override
    public DefaultResponse getDiary(User user, Long id) {
        try{
            Optional<Diary> diary = diaryJpaRepository.findById(id);

            System.out.println("diary = " + diary.get().getUser().getId() + diary.get().getUser().getNickname());

            GetDiaryResponse response = GetDiaryResponse.builder()
                    .id(diary.get().getId())
                    .writeDate(diary.get().getWriteDate())
                    .editionDate(diary.get().getEditionDate())
                    .content(diary.get().getContent())
                    .emotion(diary.get().getEmotion())
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
    public DefaultResponse getDiaryList(User user) {

//        Optional<User> findUser = userJpaRepository.findById(userId);
//        if(User == null){
//            return DefaultResponse.response(
//                    StatusCode.BAD_REQUEST,
//                    ResponseMessage.USER_SEARCH_FAIL
//            );
//        }
        List<Diary> diaryList = diaryJpaRepository.findAllById(user.getId());

        ObjectMapper mapper = new ObjectMapper();
        List<HashMap<String, String>> mapList = new ArrayList<HashMap<String, String>>();

        for (Diary diary : diaryList) {
            HashMap<String, String> map= new HashMap<String, String>();
            map.put("id", diary.getId().toString());
            map.put("writeDate", diary.getWriteDate().toString());
            map.put("content", diary.getContent());
            map.put("thumbnail", diary.getThumbnailPath());
            map.put("emotion", Integer.toString(diary.getEmotion()));

            mapList.add(map);
        }
        try{
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mapList);
//            List<Diary> diaryList = diaryJpaRepository.findAllById(user.getId());

            GetDiaryListResponse diaryListResponse = GetDiaryListResponse
                    .builder()
                    .diaryListJsonData(json)
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
    public DefaultResponse patchDiary(PatchDiaryRequest request, List<String> files) {
        try {
            //다이어리 및 다이어리 리스트 가져오기
            Diary diary = diaryJpaRepository.getById(request.getId());
            List<DiaryImage> diaryImages= diaryImageJpaRepository.findByDiaryId(request.getId());

            // 텍스트 분석을 통한 감정 추출에 대한 설정 및 구현
            TextAnalyticsClient client = analyticService.authenticateClient();
            int emotion = analyticService.AnalysisSentiment(client,request.getContent());

            // 다이어리 내용과 감정 변경
            diary.setEditionDate(LocalDateTime.now());
            diary.setContent(request.getContent());
            diary.setEmotion(emotion);

            // iterator를 만들어서 해당 내용에 대해 반복문 수행 및 설정
            Iterator<String> fileIterator = files.iterator();
            Iterator<String> commentIterator =  request.getComment().iterator();
            Iterator<DiaryImage> diaryImageIterator = diaryImages.listIterator();

            while (fileIterator.hasNext()
                    && commentIterator.hasNext()
                    && diaryImageIterator.hasNext()
            ) {
                DiaryImage diaryImage = diaryImageIterator.next();
                diaryImage.setImage_path(fileIterator.next());
                diaryImage.setComment(commentIterator.next());
                diaryImageJpaRepository.save(diaryImageIterator.next());
            }

            diaryJpaRepository.save(diary);

            // 반환되고 난 이후에 대한 반환값 지정
            PatchDiaryResponse patchDiaryResponse = PatchDiaryResponse
                    .builder()
                    .user(diary.getUser())
                    .writeTime(diary.getWriteDate())
                    .editionDate(diary.getEditionDate())
                    .content(diary.getContent())
                    .emotion(diary.getEmotion())
                    .build();

            DefaultResponse response = DefaultResponse.response(
                    StatusCode.OK,
                    ResponseMessage.DIARY_PATCH_SUCCESS,
                    patchDiaryResponse
            );
            return response;
        }
        catch (Exception e){
            log.error(e.getMessage());
            return DefaultResponse.response(
                    StatusCode.BAD_REQUEST,
                    ResponseMessage.INVALID_DIARY
            );
        }
    }

    // return void value
    @Override
    @Transactional
    public DefaultResponse deleteDiary(Long id) {

        Optional<Diary> diary = diaryJpaRepository.findById(id);

        if(diary.isEmpty()){
            return DefaultResponse.response(
                    StatusCode.BAD_REQUEST,
                    ResponseMessage.INVALID_DIARY
            );
        }

        try {
            diaryJpaRepository.delete(diary.get());
            DeleteDiaryResponse response = DeleteDiaryResponse
                    .builder()
                    .id(id)
                    .build();

            return DefaultResponse.response(
                    StatusCode.OK,
                    ResponseMessage.DIARY_DELETE_SUCCESS,
                    response
            );
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(e.getMessage());
            e.printStackTrace();
            return DefaultResponse.response(
                    StatusCode.INTERNAL_SERVER_ERROR,
                    ResponseMessage.Diary_DELETE_FAIL
            );
        }
    }

    @Override
    public DefaultResponse getDiaryByLocalDate(User user, LocalDate date) {
        try{
            Diary diary = diaryJpaRepository.findByLocalDate(user.getId(), date);

            DefaultResponse response = DefaultResponse.builder()
                    .status(StatusCode.OK)
                    .message(ResponseMessage.DIARY_GET_SUCCESS)
                    .data(diary)
                    .build();

            return response;

        } catch (Exception e){
            log.error(e.getMessage());
            return DefaultResponse.response(
                    StatusCode.DB_ERROR,
                    ResponseMessage.DIARY_GET_FAIL,
                    e.getMessage());
        }
    }

    @Override
    public DefaultResponse getDiaryListByLocalDate(User user, LocalDate startDate, LocalDate endDate) {
        try{
            List<Diary> diaryList = diaryJpaRepository.findListByLocalDate(user.getId(),startDate, endDate);

            ObjectMapper mapper = new ObjectMapper();
            List<HashMap<String, String>> mapList = new ArrayList<HashMap<String, String>>();

            for (Diary diary : diaryList) {
                HashMap<String, String> map= new HashMap<String, String>();
                map.put("id", diary.getId().toString());
                map.put("writeDate", diary.getWriteDate().toString());
                map.put("content", diary.getContent());
                map.put("thumbnail", diary.getThumbnailPath());
                map.put("emotion", Integer.toString(diary.getEmotion()));

                mapList.add(map);
            }

            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mapList);

            DefaultResponse response = DefaultResponse.builder()
                    .status(StatusCode.OK)
                    .message(ResponseMessage.DIARY_GET_SUCCESS)
                    .data(json)
                    .build();

            return response;

        } catch (Exception e){
            log.error(e.getMessage());
            return DefaultResponse.response(
                    StatusCode.DB_ERROR,
                    ResponseMessage.DIARY_GET_FAIL,
                    e.getMessage());
        }
    }

    private Boolean validateCheckDiary(Diary diary){
        return null;
    }
}
