package UPF2022SS4.KoonsDiarySpring.repository;

import UPF2022SS4.KoonsDiarySpring.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Long>,  UserRepository{
}
