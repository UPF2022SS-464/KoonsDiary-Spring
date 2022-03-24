package UPF2022SS4.KoonsDiarySpring.repository;

import UPF2022SS4.KoonsDiarySpring.domain.User;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository{

    public User findByName(String username);
    public User findByEmail(String email);
    public List<User> findByKakaoKey(Long kakaoKey);
    public List<User> findAll();
}
