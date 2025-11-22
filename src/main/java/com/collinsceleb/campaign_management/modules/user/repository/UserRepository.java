package com.collinsceleb.campaign_management.modules.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.collinsceleb.campaign_management.modules.user.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, java.util.UUID> {
    boolean existsByEmail(String email);

    Optional<UserEntity> findByEmail(String email);
}