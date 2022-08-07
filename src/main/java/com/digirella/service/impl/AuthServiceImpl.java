package com.digirella.service.impl;

import com.digirella.dto.request.LoginRequest;
import com.digirella.dto.response.JwtResponse;
import com.digirella.service.AuthService;
import com.digirella.util.TokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final TokenManager tokenManager;
    private final AuthenticationManager authenticationManager;

    @Override
    public JwtResponse login(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            return JwtResponse.builder()
                    .token(tokenManager.generateToken(loginRequest.getUsername()))
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return JwtResponse.builder().build();
    }

}
