package com.Evaluation.securexam.controller;


import com.Evaluation.securexam.dto.request.LogoutRequest;
import com.Evaluation.securexam.dto.request.RefreshTokenRequest;
import com.Evaluation.securexam.dto.request.RegisterRequest;
import com.Evaluation.securexam.dto.response.LoginResponse;
import com.Evaluation.securexam.dto.response.RefreshTokenResponse;
import com.Evaluation.securexam.dto.response.RegisterResponse;
import com.Evaluation.securexam.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.Evaluation.securexam.dto.request.LoginRequest;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public RegisterResponse register(@RequestBody RegisterRequest request) {

        return authService.register(request);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {

        return authService.login(request);
    }
    @PostMapping("/logout")
    public String logout(@RequestBody LogoutRequest request) {

        return authService.logout(request);
    }

    @PostMapping("/refresh")
    public RefreshTokenResponse refreshToken(@RequestBody RefreshTokenRequest request) {

        return authService.refreshToken(request);
    }



}
