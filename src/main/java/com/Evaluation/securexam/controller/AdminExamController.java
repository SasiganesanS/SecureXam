package com.Evaluation.securexam.controller;

import com.Evaluation.securexam.dto.request.CreateExamRequest;
import com.Evaluation.securexam.dto.request.UpdateExamRequest;
import com.Evaluation.securexam.dto.response.ExamResponse;
import com.Evaluation.securexam.service.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/exams")
@RequiredArgsConstructor
public class AdminExamController {

    private final ExamService examService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ExamResponse createExam(
            @RequestBody CreateExamRequest request) {

        return examService.createExam(request);
    }
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<ExamResponse> getAllExams() {

        return examService.getAllExams();
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ExamResponse getExamById(@PathVariable Long id) {

        return examService.getExamById(id);
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ExamResponse updateExam(@PathVariable Long id, @RequestBody UpdateExamRequest request) {

        return examService.updateExam(id, request);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteExam(@PathVariable Long id) {

        return examService.deleteExam(id);
    }
}