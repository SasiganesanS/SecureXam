package com.Evaluation.securexam.dto.request;



import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Map;

@Data
public class SubmitExamRequest {

    private Map<Long, String> answers;
}
