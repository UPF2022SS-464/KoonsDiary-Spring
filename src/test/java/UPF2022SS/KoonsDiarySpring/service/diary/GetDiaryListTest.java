package UPF2022SS.KoonsDiarySpring.service.diary;

import UPF2022SS.KoonsDiarySpring.api.dto.DefaultResponse;
import UPF2022SS.KoonsDiarySpring.api.dto.diary.PostDiary;
import UPF2022SS.KoonsDiarySpring.common.StatusCode;
import UPF2022SS.KoonsDiarySpring.domain.Diary;
import UPF2022SS.KoonsDiarySpring.domain.ImagePath;
import UPF2022SS.KoonsDiarySpring.domain.User;
import UPF2022SS.KoonsDiarySpring.repository.diary.DiaryJpaRepository;
import UPF2022SS.KoonsDiarySpring.repository.image.ImageJpaRepository;
import UPF2022SS.KoonsDiarySpring.repository.user.UserJpaRepository;
import UPF2022SS.KoonsDiarySpring.service.image.ImageService;
import UPF2022SS.KoonsDiarySpring.service.user.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@SpringBootTest
@Transactional
@ExtendWith(SpringExtension.class)
class GetDiaryListTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private DiaryJpaRepository diaryJpaRepository;

    @Autowired
    private DiaryService diaryService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private ImageJpaRepository imageJpaRepository;

    @Test
    void getDiaryList() throws Exception{

        ImagePath imagePath = ImagePath.builder().path("profileN").build();
        imageJpaRepository.save(imagePath);

        User user = User.builder()
                .username("test")
                .userPwd("cucumber52")
                .email("test@gmail.com")
                .nickname("test")
                .imagePath(imagePath)
                .build();

        userJpaRepository.save(user);

        for(int i = 0;i<4; i++) {
            List<String> comment = new ArrayList<>();
            List<String> files = new ArrayList<String>();

            for(int j = 0; j <3; j++){
                files.add("test"+Integer.toString(j));
                comment.add("test"+Integer.toString(j));
            }

            PostDiary.Request request = new PostDiary.Request("어제의 꿈은 오늘 잊혀지기 위해 존재한다.", comment);
            diaryService.postDiary(request, user, files);
        }

        DefaultResponse response = diaryService.getDiaryList(user);
        Assertions.assertThat(response.getStatus()).isEqualTo(StatusCode.OK);
        System.out.println("response = " + response);
    }

    @Test
    void getDiary() throws Exception{
        ImagePath imagePath = ImagePath.builder().path("profileN").build();
        imageJpaRepository.save(imagePath);


        User user = User.builder()
                .username("test")
                .userPwd("cucumber52")
                .email("test@gmail.com")
                .nickname("test")
                .imagePath(imagePath)
                .build();

        userJpaRepository.save(user);


        Diary diary = Diary.builder()
                .user(user)
                .writeDate(LocalDate.now())
                .editionDate(LocalDateTime.now())
                .content("테스트 내용입니다.")
                .thumbnailPath("11")
                .emotion(4)
                .build();

        diaryJpaRepository.save(diary);

        DefaultResponse response = diaryService.getDiary(user, diary.getId());
        System.out.println("response = " + response);
    }

    // 19개의 다이어리 삽입 수행
    private Optional<Diary> setDiary(User user, int i){
        Diary diary = Diary.builder()
                .user(user)
                .writeDate(LocalDate.of(2022, 5, i))
                .editionDate(LocalDateTime.now())
                .content("반가워요")
                .emotion(1).thumbnailPath("thumbnailPath1").build();
        diaryJpaRepository.save(diary);
        Optional<Diary> findDiary = diaryJpaRepository.findByWriteDate(LocalDate.now());
        return findDiary;
    }
    //클래스에 트랜잭션 걸어두면 beforeeach 언노테이션 걸어도 메소드 호출 끝나는대로 데이터베이스가 빈다.
    /*
    * 사전 데이터 세팅 부분
    */
//    @BeforeEach
//     void createUser(){
//        User user1 = User.builder()
//                .username("test1")
//                .password("cucumber52")
//                .email("test1@gmail.com")
//                .nickname("test1")
//                .build();
//        userService.join(user1);
//    }

//    @BeforeEach
//    void createDiary(){
//        User user = userService.findUsername("test1");
//
//        List<String> comment = new ArrayList<>();
//        List<String> files = new ArrayList<String>();
//
//        for(int i = 0; i <3; i++){
//            files.add("test"+Integer.toString(i));
//            comment.add("test"+Integer.toString(i));
//        }
//
//        PostDiaryRequest postDiaryRequest = PostDiaryRequest
//                .builder()
//                .writeDate(LocalDate.now())
//                .editionDate(LocalDateTime.now())
//                .content("어제의 꿈은 오늘 잊혀지기 위해 존재한다.")
//                .comment(comment)
//                .build();
//
//        diaryService.postDiary(postDiaryRequest, user.getId(), files);
//    }
}