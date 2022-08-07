package com.digirella.unit.service;

import com.digirella.dto.request.LoginRequest;
import com.digirella.dto.response.JwtResponse;
import com.digirella.service.impl.AuthServiceImpl;
import com.digirella.util.TokenManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class AuthServiceImplTest {

    private AuthServiceImpl authService;

    @Mock
    private TokenManager tokenManager;

    @Mock
    private AuthenticationManager authenticationManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        authService = new AuthServiceImpl(tokenManager, authenticationManager);
    }

    @Test
    void loginUser_providedValidCredentials_successfullyLoggedIn() {
        LoginRequest request = LoginRequest.builder()
                .username("admin")
                .password("admin")
                .build();

        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlzcyI6Ind3dy5kaWdpcmVsbGEuYXoiLCJpYXQiOjE2NTk4NjQ" +
                "1MjgsImV4cCI6MTY1OTg2NDgyOH0.CyvdJafF228FY1qt2O0FqDIx3OmIXP7zXgKz7F0gfbg";

        when(tokenManager.generateToken(any())).thenReturn(token);
        JwtResponse jwtResponse = authService.login(request);

        assertNotNull(jwtResponse);
        assertNotNull(jwtResponse.getToken());
        assertEquals(token, jwtResponse.getToken());
    }
}
