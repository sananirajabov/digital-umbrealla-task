package com.digirella.controller;

import com.digirella.dto.request.LoginRequest;
import com.digirella.dto.response.JwtResponse;
import com.digirella.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Represents the interface for User entity manipulation.
 */
@Tag(name = "Auth Controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    /**
     * Log in user
     *
     * @param loginRequest the details of credentials
     * @return generated JWT
     */
    @Operation(description = "Log in user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Logged in successfully")
    })
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

}
