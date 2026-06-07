package com.Evaluation.securexam.service;


import com.Evaluation.securexam.dto.request.LogoutRequest;
import com.Evaluation.securexam.dto.request.RefreshTokenRequest;
import com.Evaluation.securexam.dto.response.RefreshTokenResponse;
import com.Evaluation.securexam.entity.RefreshToken;
import com.Evaluation.securexam.dto.request.LoginRequest;
import com.Evaluation.securexam.dto.request.RegisterRequest;
import com.Evaluation.securexam.dto.response.LoginResponse;
import com.Evaluation.securexam.dto.response.RegisterResponse;
import com.Evaluation.securexam.entity.User;
import com.Evaluation.securexam.repository.UserRepository;
import com.Evaluation.securexam.security.CustomUserDetailsService;
import com.Evaluation.securexam.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;
    private final RefreshTokenService refreshTokenService;


    public RegisterResponse register(RegisterRequest request) {

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(
                                request.getPassword()
                        )
                )
                .role(request.getRole())
                .build();

        userRepository.save(user);

        return new RegisterResponse(
                "User Registered Successfully"
        );
    }


    public LoginResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByUsername(request.getUsername()).orElseThrow();

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(request.getUsername());

        String accessToken = jwtService.generateAccessToken(userDetails);

        String refreshToken = jwtService.generateRefreshToken(userDetails);




        refreshTokenService.saveRefreshToken(user, refreshToken,
                LocalDateTime.now()
                        .plusSeconds(
                                jwtService.getRefreshExpiration()
                                        / 1000
                        )
        );


        return new LoginResponse(
                accessToken,
                refreshToken,
                "Bearer",
                userDetails.getUsername(),
                userDetails.getAuthorities()
                        .iterator()
                        .next()
                        .getAuthority()
        );
    }

    public RefreshTokenResponse refreshToken(RefreshTokenRequest request) {

        RefreshToken refreshToken = refreshTokenService.findByToken(request.getRefreshToken());

        if (refreshToken.getExpiryDate().isBefore(LocalDateTime.now())) {

            throw new RuntimeException(
                    "Refresh token expired"
            );
        }

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(refreshToken
                                        .getUser()
                                        .getUsername()
                        );

        String accessToken = jwtService.generateAccessToken(userDetails);

        return new RefreshTokenResponse(accessToken,request.getRefreshToken());
    }
    public String logout(LogoutRequest request) {

        User user = userRepository.findByUsername(request.getUsername())
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "User not found"
                                )
                        );

        refreshTokenService.deleteByUser(user);

        return "Logged out successfully";
    }
}
