package com.Evaluation.securexam.repository;



import com.Evaluation.securexam.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findAll();
    List<Question> findByExamId(Long examId);

}
