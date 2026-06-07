package com.Evaluation.securexam.repository;



import com.Evaluation.securexam.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
