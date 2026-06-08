package com.Evaluation.securexam.dto.request;

import lombok.Data;

@Data
public class CreateQuestionRequest {
    private String questionText;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String correctAnswer;
    private Integer marks;
}
