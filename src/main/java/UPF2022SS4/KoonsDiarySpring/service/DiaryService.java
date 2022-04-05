package UPF2022SS4.KoonsDiarySpring.service;


import UPF2022SS4.KoonsDiarySpring.api.dto.DefaultResponse;
import UPF2022SS4.KoonsDiarySpring.api.dto.diary.GetDiaryRequest;
import UPF2022SS4.KoonsDiarySpring.api.dto.diary.PostDiaryRequest;
import UPF2022SS4.KoonsDiarySpring.domain.Diary;
import UPF2022SS4.KoonsDiarySpring.domain.User;
import org.springframework.stereotype.Service;

@Service
public interface DiaryService {
    // 다이어리에 대한 CRUD
    public DefaultResponse postDiary(PostDiaryRequest postDiaryRequest);

    public DefaultResponse getDiary(GetDiaryRequest getDiaryRequest);

    public DefaultResponse getDiaryList(User user);

    public DefaultResponse patchDiary(Diary diary);

    public DefaultResponse deleteDiary(Diary diary);

    // 감정 분석 api 서비스

    // 감정 선택 서비스

    // 사용자 감정 선택
}
