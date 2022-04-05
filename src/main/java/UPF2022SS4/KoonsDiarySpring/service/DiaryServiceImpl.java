package UPF2022SS4.KoonsDiarySpring.service;

import UPF2022SS4.KoonsDiarySpring.api.dto.DefaultResponse;
import UPF2022SS4.KoonsDiarySpring.api.dto.diary.*;
import UPF2022SS4.KoonsDiarySpring.common.ResponseMessage;
import UPF2022SS4.KoonsDiarySpring.common.StatusCode;
import UPF2022SS4.KoonsDiarySpring.domain.Diary;
import UPF2022SS4.KoonsDiarySpring.domain.User;
import UPF2022SS4.KoonsDiarySpring.repository.diary.DiaryJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DiaryServiceImpl implements DiaryService{

    @Autowired
    private DiaryJpaRepository diaryJpaRepository;

    @Override
    @Transactional
    public DefaultResponse postDiary(PostDiaryRequest postDiaryRequest) {
        if (postDiaryRequest == null){
            return DefaultResponse.response(
                    StatusCode.BAD_REQUEST,
                    ResponseMessage.DIARY_POST_FAIL
            );
        }
        try{
            Diary diary = Diary.builder()
                            .user(postDiaryRequest.getUser())
                            .writeDate(postDiaryRequest.getWriteDate())
                            .editionDate(postDiaryRequest.getEditionDate())
                            .content(postDiaryRequest.getContent())
                            .emotion(postDiaryRequest.getEmotion())
                            .diaryImageList(postDiaryRequest.getDiaryImageList())
                            .thumbnailPath(postDiaryRequest.getThumbnailPath())
                            .build();
            System.out.println("diary = " + diary.getId() + diary.getUser().getNickname() + diary.getContent());

            diaryJpaRepository.save(diary);

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
    public DefaultResponse getDiaryList(User user) {

        if(user == null){
            return DefaultResponse.response(
                    StatusCode.BAD_REQUEST,
                    ResponseMessage.USER_SEARCH_FAIL
            );
        }
        try{
            List<Diary> diaryList= diaryJpaRepository.findAllByUser(user);

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
                    ResponseMessage.DIARY_GET_FAIL
            );
        }
    }

    // return diary
    @Override
    public DefaultResponse patchDiary(Diary diary) {
        return null;
    }

    // return void value
    @Override
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
