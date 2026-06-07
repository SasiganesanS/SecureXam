package com.Evaluation.securexam.dto.response;



import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ExamResponse {

    private Long id;
    private String title;
    private String description;
    private Integer duration;
    private Integer totalMarks;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
