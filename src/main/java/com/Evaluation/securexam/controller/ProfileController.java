package com.Evaluation.securexam.controller;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileController {

    @GetMapping("/profile")
    public Authentication profile() {

        return SecurityContextHolder
                .getContext()
                .getAuthentication();
    }
}
