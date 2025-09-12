package org.skypro.question_generator.repository;

import org.junit.jupiter.api.Test;
import org.skypro.question_generator.domain.Question;

import static org.junit.jupiter.api.Assertions.*;

class JavaQuestionRepositoryTest {
    private final JavaQuestionRepository repository = new JavaQuestionRepository();

    @Test
    void add_shouldAddQuestion() {
        Question question = new Question("Test", "Answer");
        Question result = repository.add(question);

        assertEquals(question, result);
        assertTrue(repository.contains(question));
    }

    @Test
    void remove_shouldRemoveExistingQuestion() {
        Question question = new Question("Test", "Answer");
        repository.add(question);

        Question result = repository.remove(question);

        assertEquals(question, result);
        assertFalse(repository.contains(question));
    }

    @Test
    void remove_shouldReturnNullForNonExistingQuestion() {
        Question question = new Question("Test", "Answer");
        Question result = repository.remove(question);

        assertNull(result);
    }

    @Test
    void getAll_shouldReturnImmutableCopy() {
        Question question = new Question("Test", "Answer");
        repository.add(question);

        var firstCall = repository.getAll();
        repository.add(new Question("Another", "Answer"));
        var secondCall = repository.getAll();

        assertEquals(1, firstCall.size());
        assertEquals(2, secondCall.size());
    }
}
