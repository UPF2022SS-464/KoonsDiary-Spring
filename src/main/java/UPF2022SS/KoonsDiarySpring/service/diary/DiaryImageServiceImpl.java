package UPF2022SS.KoonsDiarySpring.service.diary;

import UPF2022SS.KoonsDiarySpring.api.dto.DefaultResponse;
import UPF2022SS.KoonsDiarySpring.api.dto.diary.PostDiaryImageRequest;
import UPF2022SS.KoonsDiarySpring.common.ResponseMessage;
import UPF2022SS.KoonsDiarySpring.common.StatusCode;
import UPF2022SS.KoonsDiarySpring.domain.Diary;
import UPF2022SS.KoonsDiarySpring.domain.DiaryImage;
import UPF2022SS.KoonsDiarySpring.repository.diary.DiaryImageJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@Service
@Transactional
public class DiaryImageServiceImpl implements DiaryImageService{

    @Autowired
    private DiaryImageJpaRepository diaryImageJpaRepository;
    @Override
    public DefaultResponse saveImage(PostDiaryImageRequest postDiaryImageRequest) {
        try{
            DiaryImage diaryImage = DiaryImage.builder()
                    .image_path(postDiaryImageRequest.getImage_path())
                    .comment(postDiaryImageRequest.getComment())
                    .build();

            diaryImageJpaRepository.save(diaryImage);
            return DefaultResponse.response(
                    StatusCode.OK,
                    ResponseMessage.DIARY_IMAGE_POST_SUCCESS

            );
        }catch (Exception e){
            return DefaultResponse.response(StatusCode.INTERNAL_SERVER_ERROR,
                    ResponseMessage.DIARY_IMAGE_POST_FAIL,
                    e.getMessage()
                    );
        }
    }

    @Override
    public DefaultResponse setImageId(DiaryImage diaryImage, Diary diary) {
        try{
            diaryImage.setDiary(diary);
            return DefaultResponse.response(StatusCode.OK,
                    ResponseMessage.DIARY_IMAGE_MAPPING_SUCCESS
            );
        }catch (Exception e){
            return DefaultResponse.response(StatusCode.DB_ERROR,
                    ResponseMessage.DIARY_IMAGE_MAPPING_FAIL,
                    e.getMessage());
        }
    }

    @Override
    public DefaultResponse getImage(Long diaryId) {
        try{
            List<DiaryImage> diaryImageList = diaryImageJpaRepository.findByDiaryId(diaryId);
            return DefaultResponse.response(StatusCode.OK,
                    ResponseMessage.DIARY_IMAGE_GET_SUCCESS,
                    diaryImageList);
        } catch (Exception e)
        {
            return DefaultResponse.response(StatusCode.INTERNAL_SERVER_ERROR,
                    ResponseMessage.DIARY_IMAGE_GET_FAIL,
                    e.getMessage());
        }
    }
}
