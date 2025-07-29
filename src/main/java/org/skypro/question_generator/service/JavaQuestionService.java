package org.skypro.question_generator.service;

import org.skypro.question_generator.domain.Question;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class JavaQuestionService implements QuestionService {
    private final Set<Question> questions = new HashSet<>();
    private final Random random = new Random();

    @Override
    public Question add(String question, String answer) {
        Question newQuestion = new Question(question, answer);
        questions.add(newQuestion);
        return newQuestion;
    }

    @Override
    public Question remove(String question, String answer) {
        Question questionToRemove = new Question(question, answer);
        if (questions.remove(questionToRemove)) {
            return questionToRemove;
        }
        return null;
    }

    @Override
    public Collection<Question> getAll() {
        return Set.copyOf(questions);
    }

    @Override
    public Question getRandomQuestion() {
        if (questions.isEmpty()) {
        return null;
    }
        int index = random.nextInt(questions.size());
        return questions.stream()
                .skip(index)
                .findFirst()
                .orElseThrow();
    }
}
