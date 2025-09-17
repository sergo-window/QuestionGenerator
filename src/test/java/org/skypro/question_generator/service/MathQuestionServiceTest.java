package org.skypro.question_generator.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.question_generator.domain.Question;
import org.skypro.question_generator.repository.MathQuestionRepository;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MathQuestionServiceTest {

    @Mock
    private MathQuestionRepository repository;

    @InjectMocks
    private MathQuestionService mathQuestionService;

    @BeforeEach
    void setUp() {
        reset(repository);
    }

    @Test
    void add_shouldDelegateToRepository() {
        Question expected = new Question("10 / 2", "5");
        when(repository.add(any())).thenReturn(expected);

        Question result = mathQuestionService.add("10 / 2", "5");

        assertEquals(expected, result);
        verify(repository).add(any(Question.class));
    }

    @Test
    void remove_shouldDelegateToRepository() {
        Question expected = new Question("8 - 3", "5");
        when(repository.remove(any())).thenReturn(expected);

        Question result = mathQuestionService.remove("8 - 3", "5");

        assertEquals(expected, result);
        verify(repository).remove(any(Question.class));
    }

    @Test
    void getAll_shouldDelegateToRepository() {
        Set<Question> expected = Set.of(
                new Question("2 + 2", "4"),
                new Question("3 * 3", "9")
        );
        when(repository.getAll()).thenReturn(expected);

        var result = mathQuestionService.getAll();

        assertEquals(expected, result);
        verify(repository).getAll();
    }

    @Test
    void getRandomQuestion_shouldReturnQuestionFromRepository() {
        Set<Question> questions = Set.of(
                new Question("4 + 4", "8"),
                new Question("6 / 2", "3")
        );
        when(repository.getAll()).thenReturn(questions);

        Question result = mathQuestionService.getRandomQuestion();

        assertNotNull(result);
        assertTrue(questions.contains(result));
    }
}
