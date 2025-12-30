package com.codereview.ai_code_review.service.Impl;

import com.codereview.ai_code_review.dto.request.LoginRequest;
import com.codereview.ai_code_review.dto.request.RegisterRequest;
import com.codereview.ai_code_review.dto.response.AuthResponse;
import com.codereview.ai_code_review.entity.User;
import com.codereview.ai_code_review.exception.InvalidCredentialsException;
import com.codereview.ai_code_review.exception.UserAlreadyExistsException;
import com.codereview.ai_code_review.repository.UserRepository;
import com.codereview.ai_code_review.security.JWTService;
import com.codereview.ai_code_review.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           JWTService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public AuthResponse registerUser(RegisterRequest request) {
        // Check if username already exists
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UserAlreadyExistsException("Username already exists: " + request.getUsername());
        }

        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Email already exists: " + request.getEmail());
        }

        // Create new user
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // Hash password
        user.setFullName(request.getFullName());
        user.setRole(User.Role.USER);
        user.setEnabled(true);

        // Save user
        User savedUser = userRepository.save(user);

        // Generate JWT token
        String token = jwtService.generateToken(savedUser.getUsername());

        // Return response
        return new AuthResponse(
                token,
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail()
        );
    }

    @Override
    public AuthResponse loginUser(LoginRequest request) {
        // Find user by username
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid username or password"));

        // Verify password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid username or password");
        }

        // Check if user is enabled
        if (!user.isEnabled()) {
            throw new InvalidCredentialsException("Account is disabled");
        }

        // Generate JWT token
        String token = jwtService.generateToken(user.getUsername());

        // Return response
        return new AuthResponse(
                token,
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
