package org.skypro.question_generator.service;

import org.skypro.question_generator.domain.Question;
import org.skypro.question_generator.repository.MathQuestionRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MathQuestionService implements QuestionService {
    private final MathQuestionRepository repository;
    private final Random random = new Random();

    public MathQuestionService(MathQuestionRepository repository) {
        this.repository = repository;
        initializeMathQuestions();
    }

    private void initializeMathQuestions() {
        repository.add(new Question("2 + 2?", "4"));
        repository.add(new Question("5 * 5", "25"));
        repository.add(new Question("Square root of 16", "4"));
        repository.add(new Question("Derivative of x^2", "2x"));
        repository.add(new Question("10 - 3", "7"));
        repository.add(new Question("Area of circle (πr²)", "πr²"));
    }

    @Override
    public Question add(String question, String answer) {
        Question newQuestion = new Question(question, answer);
        return repository.add(newQuestion);
    }

    @Override
    public Question remove(String question, String answer) {
        Question questionToRemove = new Question(question, answer);
        return repository.remove(questionToRemove);
    }

    @Override
    public Collection<Question> getAll() {
        return repository.getAll();
    }

    @Override
    public Question getRandomQuestion() {
        Collection<Question> allQuestions = repository.getAll();
        if (allQuestions.isEmpty()) {
            return null;
        }
        int index = random.nextInt(allQuestions.size());
        return allQuestions.stream()
                .skip(index)
                .findFirst()
                .orElseThrow();
    }
}
