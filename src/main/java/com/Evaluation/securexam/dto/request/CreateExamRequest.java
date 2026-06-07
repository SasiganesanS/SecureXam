package com.Evaluation.securexam.dto.request;



import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateExamRequest {

    private String title;
    private String description;
    private Integer duration;
    private Integer totalMarks;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
