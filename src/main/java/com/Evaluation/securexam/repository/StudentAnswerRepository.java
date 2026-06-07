package com.Evaluation.securexam.repository;



import com.Evaluation.securexam.entity.StudentAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentAnswerRepository extends JpaRepository<StudentAnswer, Long> {
}
