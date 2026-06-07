package com.Evaluation.securexam.dto.request;



import lombok.Data;

import java.util.Map;

@Data
public class SubmitExamRequest {

    private Long examId;

    private Map<Long, String> answers;
}
