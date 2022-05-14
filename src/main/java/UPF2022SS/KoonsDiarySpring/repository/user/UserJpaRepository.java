package UPF2022SS.KoonsDiarySpring.repository.user;

import UPF2022SS.KoonsDiarySpring.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Long>, UserRepository{


//    Optional<User> findByUsernameAndPassw(String name);
    Optional<User> findByUsername(String userName);

    @Override
    List<User> findAllById(Iterable<Long> longs);
}
