package UPF2022SS.KoonsDiarySpring.service.diary;


import UPF2022SS.KoonsDiarySpring.api.dto.diary.*;
import UPF2022SS.KoonsDiarySpring.api.dto.DefaultResponse;
import UPF2022SS.KoonsDiarySpring.domain.User;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface DiaryService {
    // 다이어리에 대한 CRUD
    public DefaultResponse postDiary(PostDiary.Request request, User user, List<String> files);

    public DefaultResponse getDiary(User user, Long id);

    public DefaultResponse getDiaryList(User user);

    public DefaultResponse patchDiary(PatchDiaryRequest request, List<String> files);

    public DefaultResponse deleteDiary(Long id);

    public DefaultResponse getDiaryByLocalDate(User user, LocalDate date);

    public DefaultResponse getDiaryListByLocalDate(User user, LocalDate startDate, LocalDate endDate);

    // 감정 분석 api 서비스

    // 감정 선택 서비스

    // 사용자 감정 선택
}
