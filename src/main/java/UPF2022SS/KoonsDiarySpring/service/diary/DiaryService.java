package UPF2022SS.KoonsDiarySpring.service.diary;


import UPF2022SS.KoonsDiarySpring.api.dto.diary.*;
import UPF2022SS.KoonsDiarySpring.api.dto.DefaultResponse;
import UPF2022SS.KoonsDiarySpring.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface DiaryService {
    // 다이어리에 대한 CRUD
    public ResponseEntity<Object> postDiary(PostDiary.Request request, User user, List<String> files);

    public ResponseEntity<Object> getDiary(User user, Long id);

    public ResponseEntity<Object> getDiaryList(User user);

    public ResponseEntity<Object> patchDiary(PatchDiaryRequest request, List<String> files);

    public ResponseEntity<Object> deleteDiary(Long id);

    public DefaultResponse getDiaryByLocalDate(User user, LocalDate date);

    public ResponseEntity<Object> getEmotionListByLocalDate(User user, LocalDate startDate, LocalDate endDate);

    public DefaultResponse getMonthlyDiaryListByLocalDate(User user, LocalDate startDate, LocalDate endDate);

    // 감정 분석 api 서비스

    // 감정 선택 서비스

    // 사용자 감정 선택
}
