package com.Evaluation.securexam.service;

import com.Evaluation.securexam.dto.request.CreateExamRequest;
import com.Evaluation.securexam.dto.request.UpdateExamRequest;
import com.Evaluation.securexam.dto.response.ExamResponse;
import com.Evaluation.securexam.entity.Exam;
import com.Evaluation.securexam.exception.ResourceNotFoundException;
import com.Evaluation.securexam.repository.ExamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExamService {

    private final ExamRepository examRepository;

    public ExamResponse createExam(CreateExamRequest request) {

        Exam exam = Exam.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .duration(request.getDuration())
                .totalMarks(request.getTotalMarks())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .build();

        Exam savedExam =
                examRepository.save(exam);

        return ExamResponse.builder()
                .id(savedExam.getId())
                .title(savedExam.getTitle())
                .description(savedExam.getDescription())
                .duration(savedExam.getDuration())
                .totalMarks(savedExam.getTotalMarks())
                .startTime(savedExam.getStartTime())
                .endTime(savedExam.getEndTime())
                .build();
    }
        public Page<ExamResponse> getAllExams(int page, int size) {

            Pageable pageable =
                    PageRequest.of(
                            page,
                            size,
                            Sort.by("id")
                                    .ascending()
                    );

            return examRepository
                    .findAll(pageable)
                    .map(exam ->
                            ExamResponse.builder()
                                    .id(exam.getId())
                                    .title(exam.getTitle())
                                    .description(exam.getDescription())
                                    .duration(exam.getDuration())
                                    .totalMarks(exam.getTotalMarks())
                                    .startTime(exam.getStartTime())
                                    .endTime(exam.getEndTime())
                                    .build()
                    );
        }
    public ExamResponse getExamById(Long id) {

        Exam exam = examRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Exam not found"
                        ));

        return ExamResponse.builder()
                .id(exam.getId())
                .title(exam.getTitle())
                .description(exam.getDescription())
                .duration(exam.getDuration())
                .totalMarks(exam.getTotalMarks())
                .startTime(exam.getStartTime())
                .endTime(exam.getEndTime())
                .build();
    }
    public ExamResponse updateExam(Long id, UpdateExamRequest request) {

        Exam exam = examRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Exam not found"
                        ));

        exam.setTitle(request.getTitle());
        exam.setDescription(request.getDescription());
        exam.setDuration(request.getDuration());
        exam.setTotalMarks(request.getTotalMarks());
        exam.setStartTime(request.getStartTime());
        exam.setEndTime(request.getEndTime());

        Exam updatedExam = examRepository.save(exam);

        return ExamResponse.builder()
                .id(updatedExam.getId())
                .title(updatedExam.getTitle())
                .description(updatedExam.getDescription())
                .duration(updatedExam.getDuration())
                .totalMarks(updatedExam.getTotalMarks())
                .startTime(updatedExam.getStartTime())
                .endTime(updatedExam.getEndTime())
                .build();
    }
    public String deleteExam(Long id) {

        Exam exam = examRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Exam not found"
                        ));

        examRepository.delete(exam);

        return "Exam deleted successfully";
    }
}
