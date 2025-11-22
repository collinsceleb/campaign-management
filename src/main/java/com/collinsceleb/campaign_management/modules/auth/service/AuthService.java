package com.collinsceleb.campaign_management.modules.auth.service;

import com.collinsceleb.campaign_management.modules.auth.dto.AuthenticationResponse;
import com.collinsceleb.campaign_management.modules.auth.dto.LoginRequest;
import com.collinsceleb.campaign_management.modules.auth.dto.RegisterRequest;

public interface AuthService {

    AuthenticationResponse register(RegisterRequest registerRequest);
    AuthenticationResponse login(LoginRequest loginRequest);
}
