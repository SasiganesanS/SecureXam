package com.Evaluation.securexam.repository;



import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;

import com.Evaluation.securexam.entity.RefreshToken;
import com.Evaluation.securexam.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken> findByUser(User user);


    @Modifying
    @Transactional
    void deleteByUser(User user);
}
