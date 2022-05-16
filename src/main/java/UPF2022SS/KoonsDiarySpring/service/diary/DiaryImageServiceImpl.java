package UPF2022SS.KoonsDiarySpring.service.diary;

import UPF2022SS.KoonsDiarySpring.api.dto.DefaultResponse;
import UPF2022SS.KoonsDiarySpring.api.dto.diary.PostDiaryImageRequest;
import UPF2022SS.KoonsDiarySpring.common.ResponseMessage;
import UPF2022SS.KoonsDiarySpring.common.StatusCode;
import UPF2022SS.KoonsDiarySpring.domain.Diary;
import UPF2022SS.KoonsDiarySpring.domain.DiaryImage;
import UPF2022SS.KoonsDiarySpring.repository.diary.DiaryImageJpaRepository;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
public class DiaryImageServiceImpl implements DiaryImageService{

    @Autowired
    private DiaryImageJpaRepository diaryImageJpaRepository;

    @Override
    @Transactional
    public DiaryImage saveImage(String filename, String comment, Diary diary) {
        try{
            DiaryImage diaryImage = DiaryImage.builder()
                    .image_path(filename)
                    .diary(diary)
                    .comment(comment)
                    .build();
            diaryImageJpaRepository.save(diaryImage);
            return diaryImage;
        }catch (Exception e){
            return DiaryImage.builder().build();
            }
    }

    @Override
    @Transactional
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
