package org.skypro.question_generator.repository;

import org.skypro.question_generator.domain.Question;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Repository
public class MathQuestionRepository implements QuestionRepository {
    private final Set<Question> questions = new HashSet<>();

    @Override
    public Question add(Question question) {
        questions.add(question);
        return question;
    }

    @Override
    public Question remove(Question question) {
        if (questions.remove(question)) {
            return question;
        }
        return null;
    }

    @Override
    public Collection<Question> getAll() {
        return Set.copyOf(questions);
    }

    @Override
    public boolean contains(Question question) {
        return questions.contains(question);
    }

    @Override
    public int size() {
        return questions.size();
    }
}
