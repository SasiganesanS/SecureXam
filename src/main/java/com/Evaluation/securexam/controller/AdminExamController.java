package com.Evaluation.securexam.controller;

import com.Evaluation.securexam.dto.request.CreateExamRequest;
import com.Evaluation.securexam.dto.request.UpdateExamRequest;
import com.Evaluation.securexam.dto.response.ExamResponse;
import com.Evaluation.securexam.service.ExamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/exams")
@RequiredArgsConstructor
public class AdminExamController {

    private final ExamService examService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ExamResponse createExam(@Valid
            @RequestBody CreateExamRequest request) {

        return examService.createExam(request);
    }
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Page<ExamResponse> getAllExams(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "5")
            int size) {

        return examService.getAllExams(
                page,
                size
        );
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ExamResponse getExamById(@Valid @PathVariable Long id) {

        return examService.getExamById(id);
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ExamResponse updateExam(@Valid @PathVariable Long id, @RequestBody UpdateExamRequest request) {

        return examService.updateExam(id, request);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteExam(@Valid @PathVariable Long id) {

        return examService.deleteExam(id);
    }
}