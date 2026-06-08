package com.Evaluation.securexam.controller;

import com.Evaluation.securexam.dto.request.SubmitExamRequest;
import com.Evaluation.securexam.dto.response.ExamAttemptResponse;
import com.Evaluation.securexam.dto.response.ResultResponse;
import com.Evaluation.securexam.dto.response.StudentQuestionResponse;
import com.Evaluation.securexam.service.AttemptService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    private final AttemptService attemptService;

    @PostMapping("/exams/{examId}/start")
    @PreAuthorize("hasRole('STUDENT')")
    public ExamAttemptResponse startExam(@PathVariable Long examId, Authentication authentication) {

        return attemptService.startExam(
                examId,
                authentication.getName()
        );
    }
    @GetMapping("/attempts/{attemptId}/questions")
    @PreAuthorize("hasRole('STUDENT')")
    public List<StudentQuestionResponse> getQuestionsForAttempt(
            @PathVariable Long attemptId) {

        return attemptService
                .getQuestionsForAttempt(attemptId);
    }
    @PostMapping("/attempts/{attemptId}/submit")
    @PreAuthorize("hasRole('STUDENT')")
    public ResultResponse submitExam(
            @PathVariable Long attemptId,
            @RequestBody SubmitExamRequest request) {

        return attemptService.submitExam(
                attemptId,
                request
        );
    }
    @GetMapping("/results/{attemptId}")
    @PreAuthorize("hasRole('STUDENT')")
    public ResultResponse getResultByAttemptId(
            @PathVariable Long attemptId,
            Authentication authentication) {

        return attemptService
                .getResultByAttemptId(
                        attemptId,
                        authentication.getName()
                );
    }
}