package com.Evaluation.securexam.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExamAttemptResponse {

    private Long attemptId;
    private Long examId;
    private String status;
}