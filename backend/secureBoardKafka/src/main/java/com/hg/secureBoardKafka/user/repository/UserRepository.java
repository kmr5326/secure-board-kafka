package com.hg.secureBoardKafka.user.repository;

import com.hg.secureBoardKafka.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUserId(String userId);
    boolean existsByUserId(String userId);
}
