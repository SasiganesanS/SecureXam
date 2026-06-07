package com.Evaluation.securexam.repository;


import com.Evaluation.securexam.entity.ExamAttempt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamAttemptRepository extends JpaRepository<ExamAttempt, Long> {
}
