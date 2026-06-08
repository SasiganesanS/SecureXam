package com.Evaluation.securexam.controller;

import com.Evaluation.securexam.dto.request.CreateQuestionRequest;
import com.Evaluation.securexam.dto.request.UpdateQuestionRequest;
import com.Evaluation.securexam.dto.response.QuestionResponse;
import com.Evaluation.securexam.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping("/admin/exams/{examId}/questions")
    @PreAuthorize("hasRole('ADMIN')")
    public QuestionResponse addQuestion(
            @PathVariable Long examId,
            @RequestBody CreateQuestionRequest request) {

        return questionService.addQuestion(
                examId,
                request
        );
    }

    @GetMapping("/admin/exams/{examId}/questions")
    @PreAuthorize("hasRole('ADMIN')")
    public List<QuestionResponse> getQuestionsByExam(
            @PathVariable Long examId) {

        return questionService.getQuestionsByExam(
                examId
        );
    }

    @GetMapping("/questions/{questionId}")
    @PreAuthorize("hasRole('ADMIN')")
    public QuestionResponse getQuestionById(
            @PathVariable Long questionId) {

        return questionService.getQuestionById(
                questionId
        );
    }

    @PutMapping("/questions/{questionId}")
    @PreAuthorize("hasRole('ADMIN')")
    public QuestionResponse updateQuestion(
            @PathVariable Long questionId,
            @RequestBody UpdateQuestionRequest request) {

        return questionService.updateQuestion(
                questionId,
                request
        );
    }

    @DeleteMapping("/questions/{questionId}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteQuestion(
            @PathVariable Long questionId) {

        return questionService.deleteQuestion(
                questionId
        );
    }
}