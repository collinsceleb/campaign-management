package com.collinsceleb.campaign_management.modules.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.collinsceleb.campaign_management.modules.auth.dto.AuthenticationResponse;
import com.collinsceleb.campaign_management.modules.auth.dto.LoginRequest;
import com.collinsceleb.campaign_management.modules.auth.dto.RegisterRequest;
import com.collinsceleb.campaign_management.modules.user.entity.UserEntity;
import com.collinsceleb.campaign_management.modules.user.repository.UserRepository;
import com.collinsceleb.campaign_management.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public AuthenticationResponse register(RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail().trim().toLowerCase())) {
            throw new RuntimeException("Email already exists");
        }
        UserEntity user = new UserEntity();
        user.setEmail(registerRequest.getEmail().trim().toLowerCase());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        userRepository.save(user);
        String token = jwtService.generateToken(user.getEmail());
        return new AuthenticationResponse(token);
    }

    @Override
    public AuthenticationResponse login(LoginRequest loginRequest) {
        UserEntity user = userRepository.findByEmail(loginRequest.getEmail().trim().toLowerCase())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }
        String token = jwtService.generateToken(user.getEmail());
        return new AuthenticationResponse(token);
    }
}
