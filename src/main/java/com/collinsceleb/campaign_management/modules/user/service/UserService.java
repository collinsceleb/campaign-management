package com.collinsceleb.campaign_management.modules.user.service;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.collinsceleb.campaign_management.modules.user.dto.UserProfileUpdateDto;
import com.collinsceleb.campaign_management.modules.user.entity.UserEntity;
import com.collinsceleb.campaign_management.modules.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserEntity updateProfile(@NonNull java.util.UUID userId, UserProfileUpdateDto request) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        return userRepository.save(user);
    }
}
