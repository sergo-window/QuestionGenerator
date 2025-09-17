package org.skypro.question_generator.service;

import org.skypro.question_generator.domain.Question;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class ExaminerServiceImpl implements ExaminerService {
    private final Map<String, QuestionService> questionServices;

    public ExaminerServiceImpl(
            JavaQuestionService javaQuestionService,
            MathQuestionService mathQuestionService) {
        this.questionServices = Map.of(
                "java", javaQuestionService,
                "math", mathQuestionService
        );
    }

    @Override
    public Collection<Question> getQuestions(String subject, int amount) {
        QuestionService questionService = questionServices.get(subject.toLowerCase());
        if (questionService == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Subject '" + subject + "' not found"
            );
        }

        Collection<Question> allQuestions = questionService.getAll();
        if (amount > allQuestions.size()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    String.format("Requested %d questions, but only %d available for subject '%s'",
                            amount, allQuestions.size(), subject)
            );
        }

        Set<Question> uniqueQuestions = new HashSet<>();
        while (uniqueQuestions.size() < amount) {
            Question randomQuestion = questionService.getRandomQuestion();
            if (randomQuestion != null) {
                uniqueQuestions.add(randomQuestion);
            }
        }

        return uniqueQuestions;
    }
}

