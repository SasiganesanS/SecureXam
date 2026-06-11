package com.Evaluation.securexam.service;

import com.Evaluation.securexam.dto.request.LoginRequest;
import com.Evaluation.securexam.dto.response.LoginResponse;
import com.Evaluation.securexam.entity.User;
import com.Evaluation.securexam.repository.UserRepository;
import com.Evaluation.securexam.security.CustomUserDetailsService;
import com.Evaluation.securexam.security.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private CustomUserDetailsService customUserDetailsService;

    @Mock
    private RefreshTokenService refreshTokenService;

    @Mock
    private org.springframework.security.authentication.AuthenticationManager authenticationManager;

    @Mock
    private org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    @Test
    void shouldLoginSuccessfully() {

        LoginRequest request = new LoginRequest();
        request.setUsername("sasi");
        request.setPassword("123");

        User user = new User();
        user.setUsername("sasi");

        UserDetails userDetails =
                new org.springframework.security.core.userdetails.User(
                        "sasi",
                        "123",
                        List.of(
                                new SimpleGrantedAuthority("ROLE_STUDENT")
                        )
                );

        when(userRepository.findByUsername("sasi"))
                .thenReturn(Optional.of(user));

        when(customUserDetailsService.loadUserByUsername("sasi"))
                .thenReturn(userDetails);

        when(jwtService.generateAccessToken(userDetails))
                .thenReturn("access-token");

        when(jwtService.generateRefreshToken(userDetails))
                .thenReturn("refresh-token");

        when(jwtService.getRefreshExpiration())
                .thenReturn(604800000L);


        LoginResponse response = authService.login(request);

        assertEquals(
                "access-token",
                response.getAccessToken()
        );

        assertEquals(
                "refresh-token",
                response.getRefreshToken()
        );

        verify(refreshTokenService)
                .saveRefreshToken(
                        eq(user),
                        eq("refresh-token"),
                        any()
                );
    }
    @Test
    void shouldThrowExceptionWhenUserNotFound() {

        LoginRequest request = new LoginRequest();
        request.setUsername("sasi");
        request.setPassword("123");

        when(userRepository.findByUsername("sasi"))
                .thenReturn(Optional.empty());

        assertThrows(
                NoSuchElementException.class,
                () -> authService.login(request)
        );
    }

}
