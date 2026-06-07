package com.Evaluation.securexam.entity;



import com.Evaluation.securexam.enums.AttemptStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "exam_attempts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExamAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private User student;

    @ManyToOne
    @JoinColumn(name = "exam_id")
    private Exam exam;

    private LocalDateTime startTime;

    private LocalDateTime submitTime;

    private Integer score;

    @Enumerated(EnumType.STRING)
    private AttemptStatus status;
}
