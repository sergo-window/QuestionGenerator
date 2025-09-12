package org.skypro.question_generator.repository;

import org.skypro.question_generator.domain.Question;

import java.util.Collection;

public interface QuestionRepository {
    Question add(Question question);

    Question remove(Question question);

    Collection<Question> getAll();

    boolean contains(Question question);

    int size();
}