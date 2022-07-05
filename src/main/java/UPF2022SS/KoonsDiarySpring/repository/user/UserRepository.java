package UPF2022SS.KoonsDiarySpring.repository.user;

import UPF2022SS.KoonsDiarySpring.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository{
    public List<User> findByContainedName(String username);
}
