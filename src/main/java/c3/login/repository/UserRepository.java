package c3.login.repository;

import c3.login.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findBySocialId(Long socialId);
    Optional<User> findById(Long id);
}
