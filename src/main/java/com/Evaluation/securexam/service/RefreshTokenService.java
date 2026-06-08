package com.Evaluation.securexam.service;

import com.Evaluation.securexam.exception.ResourceNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.Evaluation.securexam.entity.RefreshToken;
import com.Evaluation.securexam.entity.User;
import com.Evaluation.securexam.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public void saveRefreshToken(
            User user,
            String token,
            LocalDateTime expiryDate) {

        RefreshToken refreshToken =
                refreshTokenRepository
                        .findByUser(user)
                        .orElse(new RefreshToken());

        refreshToken.setUser(user);
        refreshToken.setToken(token);
        refreshToken.setExpiryDate(expiryDate);

        refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken findByToken(
            String token) {

        return refreshTokenRepository
                .findByToken(token)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Refresh Token not found"
                        ));
    }

    @Transactional
    public void deleteByUser(User user) {

        refreshTokenRepository.deleteByUser(user);
    }
}