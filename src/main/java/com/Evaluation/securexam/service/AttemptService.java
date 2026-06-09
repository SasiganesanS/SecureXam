package com.Evaluation.securexam.service;

import com.Evaluation.securexam.dto.request.SubmitExamRequest;
import com.Evaluation.securexam.dto.response.ExamAttemptResponse;
import com.Evaluation.securexam.dto.response.ResultResponse;
import com.Evaluation.securexam.dto.response.StudentQuestionResponse;
import com.Evaluation.securexam.entity.*;
import com.Evaluation.securexam.enums.AttemptStatus;
import com.Evaluation.securexam.exception.ResourceNotFoundException;
import com.Evaluation.securexam.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttemptService {

    private final StudentAnswerRepository studentAnswerRepository;
    private final ExamRepository examRepository;
    private final UserRepository userRepository;
    private final ExamAttemptRepository examAttemptRepository;
    private final QuestionRepository questionRepository;

    public ExamAttemptResponse startExam(
            Long examId,
            String username) {

        Exam exam = examRepository.findById(examId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Exam not found"
                        ));

        User student = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Exam not found"
                        ));
        LocalDateTime now = LocalDateTime.now();

        if(now.isBefore(exam.getStartTime())){
            throw new RuntimeException(
                    "Exam has not started yet"
            );
        }

        if(now.isAfter(exam.getEndTime())){
            throw new RuntimeException(
                    "Exam has already ended"
            );
        }


        ExamAttempt existingAttempt = examAttemptRepository
                        .findByStudentAndExam(student, exam).orElse(null);

        if (existingAttempt != null) {

            throw new RuntimeException("You have already attempted this exam");
        }

        ExamAttempt attempt = ExamAttempt.builder()
                        .student(student)
                        .exam(exam)
                        .startTime(LocalDateTime.now())
                        .score(0)
                        .status(AttemptStatus.STARTED)
                        .build();

        ExamAttempt savedAttempt = examAttemptRepository.save(attempt);

        return ExamAttemptResponse.builder()
                .attemptId(savedAttempt.getId())
                .examId(exam.getId())
                .status(savedAttempt.getStatus().name())
                .build();
    }
    public List<StudentQuestionResponse> getQuestionsForAttempt(
            Long attemptId) {

        ExamAttempt attempt =
                examAttemptRepository.findById(attemptId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Attempt not found"
                                ));

        Long examId =
                attempt.getExam().getId();

        return questionRepository
                .findByExamId(examId)
                .stream()
                .map(question ->
                        StudentQuestionResponse.builder()
                                .id(question.getId())
                                .questionText(
                                        question.getQuestionText())
                                .optionA(
                                        question.getOptionA())
                                .optionB(
                                        question.getOptionB())
                                .optionC(
                                        question.getOptionC())
                                .optionD(
                                        question.getOptionD())
                                .marks(
                                        question.getMarks())
                                .build()
                )
                .toList();
    }
    public ResultResponse submitExam(Long attemptId, SubmitExamRequest request) {


        ExamAttempt attempt = examAttemptRepository.findById(attemptId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Attempt not found"
                                ));
        LocalDateTime allowedEndTime =
                attempt.getStartTime()
                        .plusMinutes(
                                attempt.getExam()
                                        .getDuration()
                        );

        if(LocalDateTime.now()
                .isAfter(allowedEndTime)){

            throw new RuntimeException(
                    "Exam time is over"
            );
        }

        int score = 0;

        for (var entry :
                request.getAnswers().entrySet()) {

            Long questionId = entry.getKey();

            String selectedAnswer =
                    entry.getValue();

            Question question =
                    questionRepository.findById(questionId)
                            .orElseThrow(() ->
                                    new ResourceNotFoundException(
                                            "Question not found"
                                    ));

            StudentAnswer answer =
                    StudentAnswer.builder()
                            .attempt(attempt)
                            .question(question)
                            .selectedAnswer(selectedAnswer)
                            .build();

            studentAnswerRepository.save(answer);

            if (question.getCorrectAnswer()
                    .equalsIgnoreCase(selectedAnswer)) {

                score += question.getMarks();
            }
        }

        attempt.setScore(score);
        attempt.setSubmitTime(LocalDateTime.now());
        attempt.setStatus(AttemptStatus.SUBMITTED);

        examAttemptRepository.save(attempt);

        return ResultResponse.builder()
                .attemptId(attempt.getId())
                .score(score)
                .status(attempt.getStatus().name()
                )
                .build();
    }
    public ResultResponse getResultByAttemptId(
            Long attemptId,
            String username) {

        ExamAttempt attempt =
                examAttemptRepository.findById(attemptId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Attempt not found"
                                ));

        if (!attempt.getStudent()
                .getUsername()
                .equals(username)) {

            throw new RuntimeException("Access denied");
        }

        return ResultResponse.builder()
                .attemptId(attempt.getId())
                .examTitle(
                        attempt.getExam()
                                .getTitle())
                .score(
                        attempt.getScore())
                .totalMarks(
                        attempt.getExam()
                                .getTotalMarks())
                .status(
                        attempt.getStatus()
                                .name())
                .submittedAt(
                        attempt.getSubmitTime())
                .build();
    }

}