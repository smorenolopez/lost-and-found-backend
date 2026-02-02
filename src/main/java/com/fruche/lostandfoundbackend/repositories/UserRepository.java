package com.fruche.lostandfoundbackend.repositories;

import com.fruche.lostandfoundbackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String userame);
    boolean existsByUsername(String admin);
}
