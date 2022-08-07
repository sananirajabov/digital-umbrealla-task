package com.digirella.service;

import com.digirella.dto.request.LoginRequest;
import com.digirella.dto.response.JwtResponse;

/**
 * Defines the basic set of Business-context operations on User business entity.
 */
public interface AuthService {

    /**
     * Log in user with given parameters
     *
     * @param loginRequest the details of credentials of the user
     * @return generated JWT
     */
    JwtResponse login(LoginRequest loginRequest);
}
