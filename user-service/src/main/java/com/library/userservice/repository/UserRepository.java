package com.library.userservice.repository;

import com.library.userservice.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserProfile, Long> {

    boolean existsByEmail(String email);

    void deleteByEmail(String email);

    Optional<UserProfile> findByEmail(String email);
}