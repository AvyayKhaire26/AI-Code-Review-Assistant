//package com.codereview.ai_code_review.service;
//
//import com.codereview.ai_code_review.dto.request.LoginRequest;
//import com.codereview.ai_code_review.dto.request.RegisterRequest;
//import com.codereview.ai_code_review.dto.response.AuthResponse;
//import com.codereview.ai_code_review.entity.User;
//import com.codereview.ai_code_review.repository.UserRepository;
//import com.codereview.ai_code_review.security.JwtTokenProvider;
//import com.codereview.ai_code_review.service.Impl.AuthServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class AuthServiceTest {
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private PasswordEncoder passwordEncoder;
//
//    @Mock
//    private JwtTokenProvider tokenProvider;
//
//    @Mock
//    private AuthenticationManager authenticationManager;
//
//    @InjectMocks
//    private AuthServiceImpl authService;
//
//    private RegisterRequest registerRequest;
//    private User user;
//
//    @BeforeEach
//    void setUp() {
//        registerRequest = new RegisterRequest();
//        registerRequest.setUsername("testuser");
//        registerRequest.setEmail("test@example.com");
//        registerRequest.setPassword("password123");
//        registerRequest.setFullName("Test User");
//
//        user = new User();
//        user.setId(1L);
//        user.setUsername("testuser");
//        user.setEmail("test@example.com");
//    }
//
//    @Test
//    void testRegisterUser_Success() {
//        // Arrange
//        when(userRepository.existsByUsername(anyString())).thenReturn(false);
//        when(userRepository.existsByEmail(anyString())).thenReturn(false);
//        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
//        when(userRepository.save(any(User.class))).thenReturn(user);
//        when(tokenProvider.generateToken(anyString())).thenReturn("test-token");
//
//        // Act
//        AuthResponse response = authService.register(registerRequest);
//
//        // Assert
//        assertNotNull(response);
//        assertEquals("test-token", response.getToken());
//        assertEquals("testuser", response.getUsername());
//        verify(userRepository, times(1)).save(any(User.class));
//    }
//
//    @Test
//    void testRegisterUser_UsernameExists() {
//        // Arrange
//        when(userRepository.existsByUsername(anyString())).thenReturn(true);
//
//        // Act & Assert
//        assertThrows(RuntimeException.class, () -> authService.register(registerRequest));
//        verify(userRepository, never()).save(any(User.class));
//    }
//}
