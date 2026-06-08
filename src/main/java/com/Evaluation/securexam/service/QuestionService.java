package com.Evaluation.securexam.service;

import com.Evaluation.securexam.dto.request.CreateQuestionRequest;
import com.Evaluation.securexam.dto.request.UpdateQuestionRequest;
import com.Evaluation.securexam.dto.response.QuestionResponse;
import com.Evaluation.securexam.entity.Exam;
import com.Evaluation.securexam.entity.Question;
import com.Evaluation.securexam.repository.ExamRepository;
import com.Evaluation.securexam.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final ExamRepository examRepository;

    public QuestionResponse addQuestion(
            Long examId,
            CreateQuestionRequest request) {

        Exam exam = examRepository.findById(examId)
                .orElseThrow(() ->
                        new RuntimeException("Exam not found"));

        Question question = Question.builder()
                .questionText(request.getQuestionText())
                .optionA(request.getOptionA())
                .optionB(request.getOptionB())
                .optionC(request.getOptionC())
                .optionD(request.getOptionD())
                .correctAnswer(request.getCorrectAnswer())
                .marks(request.getMarks())
                .exam(exam)
                .build();

        Question savedQuestion =
                questionRepository.save(question);

        return mapToResponse(savedQuestion);
    }

    public List<QuestionResponse> getQuestionsByExam(
            Long examId) {

        return questionRepository.findAll()
                .stream()
                .filter(q ->
                        q.getExam()
                                .getId()
                                .equals(examId))
                .map(this::mapToResponse)
                .toList();
    }

    public QuestionResponse getQuestionById(
            Long questionId) {

        Question question =
                questionRepository.findById(questionId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Question not found"));

        return mapToResponse(question);
    }

    public QuestionResponse updateQuestion(
            Long questionId,
            UpdateQuestionRequest request) {

        Question question =
                questionRepository.findById(questionId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Question not found"));

        question.setQuestionText(
                request.getQuestionText());

        question.setOptionA(
                request.getOptionA());

        question.setOptionB(
                request.getOptionB());

        question.setOptionC(
                request.getOptionC());

        question.setOptionD(
                request.getOptionD());

        question.setCorrectAnswer(
                request.getCorrectAnswer());

        question.setMarks(
                request.getMarks());

        Question updatedQuestion =
                questionRepository.save(question);

        return mapToResponse(updatedQuestion);
    }

    public String deleteQuestion(
            Long questionId) {

        Question question =
                questionRepository.findById(questionId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Question not found"));

        questionRepository.delete(question);

        return "Question deleted successfully";
    }

    private QuestionResponse mapToResponse(
            Question question) {

        return QuestionResponse.builder()
                .questionId(question.getQuestionId())
                .questionText(question.getQuestionText())
                .optionA(question.getOptionA())
                .optionB(question.getOptionB())
                .optionC(question.getOptionC())
                .optionD(question.getOptionD())
                .correctAnswer(question.getCorrectAnswer())
                .marks(question.getMarks())
                .examId(question.getExam().getId())
                .build();
    }
}