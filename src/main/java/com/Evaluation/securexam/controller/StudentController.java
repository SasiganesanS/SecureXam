package com.Evaluation.securexam.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {

    @GetMapping("/student/test")
    @PreAuthorize("hasRole('STUDENT')")
    public String studentTest() {

        return "Welcome Student";
    }
}