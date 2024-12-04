package com.c3.cosmetic.repository;

import com.c3.cosmetic.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findBySocialId(Long socialId);
    Optional<User> findById(Long id);
}
