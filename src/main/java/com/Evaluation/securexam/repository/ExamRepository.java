package com.Evaluation.securexam.repository;


import com.Evaluation.securexam.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamRepository extends JpaRepository<Exam, Long> {
}