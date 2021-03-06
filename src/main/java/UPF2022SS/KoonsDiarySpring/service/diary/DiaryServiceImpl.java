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
import UPF2022SS.KoonsDiarySpring.service.diary.sub.S3Service;
import com.azure.ai.textanalytics.TextAnalyticsClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    private final DiaryJpaRepository diaryJpaRepository;
    private final S3Service s3Service;
    private final DiaryImageJpaRepository diaryImageJpaRepository;
    private final AnalyticService analyticService;

    @Override
    @Transactional
    public ResponseEntity<Object> postDiary(
            PostDiary.Request request,
            User user,
            List<String> files) {
        try{
            // 텍스트 분석을 통한 감정 추출에 대한 설정 및 구현
            TextAnalyticsClient client = analyticService.authenticateClient();
            //텍스트 분석기를 통한 감정값 저장
            int emotion = analyticService.AnalysisSentiment(client,request.getContent());

            List<DiaryImage> diaryImageList = new ArrayList<>();


            //다이어리 이미지 세팅 전에 다이어리 객체 세팅
            Diary diary = Diary.builder()
                            .user(user)
                            .writeDate(LocalDate.now())
                            .editionDate(LocalDateTime.now())
                            .content(request.getContent())
                            .emotion(emotion)
                            .thumbnailPath(files.get(0)) //-> image path의 첫번째 값을 가져온다.
                            .build();

//            diaryJpaRepository.save(diary);

            //image path와 comment에 대한 반복문을 위해 지정
            Iterator<String> fileIterator = files.iterator();
            Iterator<String> commentIterator = request.getComment().iterator();

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
            PostDiary.Response response= new PostDiary.Response(diary.getId(),
                    diary.getUser().getId(),
                    diary.getWriteDate(),
                    diary.getEditionDate(),
                    diary.getContent(),
                    diary.getEmotion()
            );
            diaryJpaRepository.save(diary);

            return ResponseEntity
                    .ok()
                    .body(response);

        } catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity
                    .badRequest()
                    .body(ResponseMessage.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<Object> getDiary(User user, Long id) {
        try{
            Optional<Diary> diary = diaryJpaRepository.findById(id);

            System.out.println("diary = " + diary.get().getUser().getId() + diary.get().getUser().getNickname());
//            diaryImageService.getImage(id);
            List<DiaryImage> diaryImageList = diaryImageJpaRepository.findByDiaryId(id);

            /*
            * 코멘트 없이 다이어리 이미지를 반환할 경우에는 이미지 경로만 던져주는 것이 더 나을 것이다.
            */
            List<String> imagePaths = new ArrayList<String>();
            List<String> comments = new ArrayList<String>();

            for (DiaryImage diaryImage : diaryImageList) {
                imagePaths.add(diaryImage.getImage_path());
                comments.add(diaryImage.getComment());
            }


            //다이어리 이미지 객체 자체를 반환하는 것 보다는 이미지 경로만 반환해 주는 것이 더 나을 것 같아보였습니다.
            //근데 코멘트를 생각하면 이거는 객체를 반환해 주는것이 더 나을것 같네요.
            GetDiary.Response response = new GetDiary.Response(
                    diary.get().getId(),
                    diary.get().getWriteDate(),
                    diary.get().getEditionDate(),
                    diary.get().getContent(),
                    diary.get().getEmotion(),
                    imagePaths,
                    comments
            );

            return ResponseEntity
                    .ok()
                    .body(response);
        }
        catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseMessage.DIARY_GET_FAIL);
        }
    }

    @Override
    public ResponseEntity<Object> getDiaryList(User user) {

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

            return ResponseEntity
                    .ok()
                    .body(diaryListResponse);

        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseMessage.DIARY_GET_FAIL);
        }
    }

    // return diary
    @Override
    @Transactional
    public ResponseEntity<Object> patchDiary(PatchDiaryRequest request, List<String> files) {
        try {
            //다이어리 및 다이어리 리스트 가져오기
            Diary diary = diaryJpaRepository.getById(request.getId());
            List<DiaryImage> diaryImages = diaryImageJpaRepository.findByDiaryId(request.getId());

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
            Iterator<DiaryImage> diaryImageIterator = diaryImages.iterator();

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


            return ResponseEntity.ok().body(patchDiaryResponse);
        }
        catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(ResponseMessage.INVALID_DIARY);
        }
    }

    // return void value
    @Override
    @Transactional
    public ResponseEntity<Object> deleteDiary(Long id) {

        Optional<Diary> diary = diaryJpaRepository.findById(id);

        if(diary.isEmpty()){
            return ResponseEntity
                    .badRequest()
                    .body(ResponseMessage.INVALID_DIARY);
        }

        try {
            diaryJpaRepository.delete(diary.get());
            DeleteDiaryResponse response = DeleteDiaryResponse
                    .builder()
                    .id(id)
                    .build();

            return ResponseEntity
                    .ok()
                    .body(id);

        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseMessage.Diary_DELETE_FAIL);
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
    public ResponseEntity<Object> getEmotionListByLocalDate(User user, LocalDate startDate, LocalDate endDate) {
        try{
            List<Emotion> diaryList = diaryJpaRepository.findEmotionListByLocalDate(user.getId(),startDate, endDate);

            int sum = diaryList.size();
            int[] arr = {0, 0, 0, 0, 0};

            for (Emotion emotion : diaryList) {

                arr[emotion.getEmotion()]++;
            }
//            List<Integer> array = Arrays.stream(arr).toArray()
            Map<List<Emotion>, int[]> result = new HashMap<>();
            result.put(diaryList, Arrays.stream(arr).toArray());
            return ResponseEntity.ok().body(result);

        } catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity
                    .badRequest()
                    .body(ResponseMessage.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<Object> getMonthlyDiaryListByLocalDate(User user, LocalDate startDate, LocalDate endDate) {
        try{
            List<MonthlyDiary> diaryList = diaryJpaRepository.findListByMonth(user.getId(), startDate, endDate);

            return ResponseEntity
                    .ok()
                    .body(diaryList);
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity
                    .badRequest()
                    .body(ResponseMessage.BAD_REQUEST);
        }
    }

    private Boolean validateCheckDiary(Diary diary){
        return null;
    }
}
