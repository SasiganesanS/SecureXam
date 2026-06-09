package com.Evaluation.securexam.repository;

import com.Evaluation.securexam.entity.Exam;
import com.Evaluation.securexam.entity.ExamAttempt;
import com.Evaluation.securexam.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExamAttemptRepository extends JpaRepository<ExamAttempt, Long> {
    List<ExamAttempt> findByStudentUsername(String username);
    Optional<ExamAttempt> findByStudentAndExam(
            User student,
            Exam exam
    );
}