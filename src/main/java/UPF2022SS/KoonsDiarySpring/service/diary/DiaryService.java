package UPF2022SS.KoonsDiarySpring.service.diary;


import UPF2022SS.KoonsDiarySpring.api.dto.diary.GetDiaryListRequest;
import UPF2022SS.KoonsDiarySpring.api.dto.diary.GetDiaryRequest;
import UPF2022SS.KoonsDiarySpring.api.dto.diary.PostDiaryRequest;
import UPF2022SS.KoonsDiarySpring.api.dto.DefaultResponse;
import UPF2022SS.KoonsDiarySpring.domain.Diary;
import UPF2022SS.KoonsDiarySpring.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface DiaryService {
    // 다이어리에 대한 CRUD
    public DefaultResponse postDiary(PostDiaryRequest postDiaryRequest, Long userId, List<String> files);

    public DefaultResponse getDiary(GetDiaryRequest getDiaryRequest);

    public DefaultResponse getDiaryList(GetDiaryListRequest getDiaryListRequest);

    public DefaultResponse patchDiary(Diary diary);

    public DefaultResponse deleteDiary(Diary diary);

    // 감정 분석 api 서비스

    // 감정 선택 서비스

    // 사용자 감정 선택
}
