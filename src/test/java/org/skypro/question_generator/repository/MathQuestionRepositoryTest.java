package org.skypro.question_generator.repository;

import org.junit.jupiter.api.Test;
import org.skypro.question_generator.domain.Question;

import static org.junit.jupiter.api.Assertions.*;

class MathQuestionRepositoryTest {
    private final MathQuestionRepository repository = new MathQuestionRepository();

    @Test
    void add_shouldAddMathQuestion() {
        Question question = new Question("2 + 2", "4");
        Question result = repository.add(question);

        assertEquals(question, result);
        assertTrue(repository.contains(question));
    }

    @Test
    void remove_shouldRemoveMathQuestion() {
        Question question = new Question("5 * 5", "25");
        repository.add(question);

        Question result = repository.remove(question);

        assertEquals(question, result);
        assertFalse(repository.contains(question));
    }

    @Test
    void getAll_shouldReturnAllMathQuestions() {
        repository.add(new Question("1 + 1", "2"));
        repository.add(new Question("3 * 3", "9"));

        var result = repository.getAll();

        assertEquals(2, result.size());
    }
}