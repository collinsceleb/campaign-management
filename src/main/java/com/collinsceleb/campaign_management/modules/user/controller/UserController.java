package com.collinsceleb.campaign_management.modules.user.controller;

import java.util.Objects;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.collinsceleb.campaign_management.modules.user.dto.UserProfileUpdateDto;
import com.collinsceleb.campaign_management.modules.user.entity.UserEntity;
import com.collinsceleb.campaign_management.modules.user.service.UserService;
import com.collinsceleb.campaign_management.security.CustomUSerDetails;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PatchMapping("/profile")
    public ResponseEntity<?> updateProfile(
            @RequestBody @Valid UserProfileUpdateDto request,
            @AuthenticationPrincipal CustomUSerDetails userDetails) {
        UserEntity user = userDetails.getUser();
        UserEntity updated = userService.updateProfile(Objects.requireNonNull(user.getId()), request);
        return ResponseEntity.ok(updated);
    }
}
