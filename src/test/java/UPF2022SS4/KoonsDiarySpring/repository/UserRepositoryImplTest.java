package UPF2022SS4.KoonsDiarySpring.repository;

import UPF2022SS4.KoonsDiarySpring.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryImplTest {

    @Autowired
    private UserJpaRepository userJpaRepository;

    // MethodName_StateUnderTest_ExpectedBehavior
    @Test
    public void save_rightData_Success() throws  Exception{
        // given
        User user = User.builder()
                .userid("orlando")
                .password("cucumber52")
                .nickname("ingkoon")
                .email("ing97220@naver.com")
                .build();

        // when
        userJpaRepository.save(user);
        User findUser = userJpaRepository.getById(user.getId());
        // then
        assertThat(findUser.getId()).isEqualTo(user.getId()); //-> good
    }

    @Test
    public void save_rightDate_Error() throws Exception{

        // given

        // when

        // then
    }

}