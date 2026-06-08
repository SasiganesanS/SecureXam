package com.Evaluation.securexam.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ResultResponse {

    private Long attemptId;

    private String examTitle;

    private Integer score;

    private Integer totalMarks;

    private String status;

    private LocalDateTime submittedAt;
}