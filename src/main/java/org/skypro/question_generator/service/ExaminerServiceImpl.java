package org.skypro.question_generator.service;

import org.skypro.question_generator.domain.Question;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
public class ExaminerServiceImpl implements ExaminerService {
    private final QuestionService questionService;

    public ExaminerServiceImpl(QuestionService questionService) {
        this.questionService = questionService;
        createTestData();
    }

    private void createTestData() {
        Question question = new Question("What is Java?", "Programming language");
        Question question1 = new Question("Variable types in Java", "byte, short, int, long, float, double, boolean, char");
        Question question2 = new Question("Types of collections in Java", "List, Map, Set");
        Question question3 = new Question("Memory areas in Java", "Stack, Heap");
    }

    @Override
    public Collection<Question> getQuestions(int amount) {
        Collection<Question> allQuestions = questionService.getAll();
        if (amount > allQuestions.size()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Requested amount exceeds available questions count"
            );
        }

        Set<Question> uniqueQuestions = new HashSet<>();
        while (uniqueQuestions.size() < amount) {
            Question randomQuestion = questionService.getRandomQuestion();
            uniqueQuestions.add(randomQuestion);
        }

        return uniqueQuestions;
    }
}

