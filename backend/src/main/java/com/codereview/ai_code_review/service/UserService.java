package com.codereview.ai_code_review.service;

import com.codereview.ai_code_review.dto.request.LoginRequest;
import com.codereview.ai_code_review.dto.request.RegisterRequest;
import com.codereview.ai_code_review.dto.response.AuthResponse;
import com.codereview.ai_code_review.entity.User;

import java.util.Optional;

public interface UserService {

    /**
     * Register a new user
     * @param request Registration details
     * @return AuthResponse with JWT token
     */
    AuthResponse registerUser(RegisterRequest request);

    /**
     * Authenticate user and generate JWT
     * @param request Login credentials
     * @return AuthResponse with JWT token
     */
    AuthResponse loginUser(LoginRequest request);

    /**
     * Find user by username
     * @param username Username
     * @return Optional User
     */
    Optional<User> findByUsername(String username);

    /**
     * Find user by email
     * @param email Email
     * @return Optional User
     */
    Optional<User> findByEmail(String email);

    /**
     * Check if username already exists
     * @param username Username
     * @return true if exists
     */
    boolean existsByUsername(String username);

    /**
     * Check if email already exists
     * @param email Email
     * @return true if exists
     */
    boolean existsByEmail(String email);
}
