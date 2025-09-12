package org.skypro.question_generator.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.question_generator.domain.Question;
import org.skypro.question_generator.repository.JavaQuestionRepository;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JavaQuestionServiceTest {

    @Mock
    private JavaQuestionRepository repository;

    @InjectMocks
    private JavaQuestionService javaQuestionService;

    @BeforeEach
    void setUp() {
        reset(repository);
    }

    @Test
    void add_shouldAddQuestionAndReturnIt() {

        String questionText = "What is Java?";
        String answer = "Programming language";
        Question expectedQuestion = new Question(questionText, answer);

        when(repository.add(any(Question.class))).thenReturn(expectedQuestion);

        Question result = javaQuestionService.add(questionText, answer);

        assertEquals(questionText, result.getQuestion());
        assertEquals(answer, result.getAnswer());
        verify(repository).add(any(Question.class));
    }

    @Test
    void remove_shouldRemoveQuestionAndReturnIt() {

        String questionText = "Test question";
        String answer = "Test answer";
        Question question = new Question(questionText, answer);

        when(repository.remove(any(Question.class))).thenReturn(question);

        Question result = javaQuestionService.remove(questionText, answer);

        assertEquals(question, result);
        verify(repository).remove(any(Question.class));
    }

    @Test
    void remove_shouldReturnNullWhenQuestionNotExists() {

        when(repository.remove(any(Question.class))).thenReturn(null);

        Question result = javaQuestionService.remove("Non-existent", "Question");

        assertNull(result);
        verify(repository).remove(any(Question.class));
    }

    @Test
    void getAll_shouldReturnCopyOfCollection() {

        Set<Question> mockQuestions = new HashSet<>();
        mockQuestions.add(new Question("Q1", "A1"));

        when(repository.getAll()).thenReturn(mockQuestions);

        Collection<Question> firstCall = javaQuestionService.getAll();

        mockQuestions.add(new Question("Q2", "A2"));
        when(repository.getAll()).thenReturn(Set.copyOf(mockQuestions));

        Collection<Question> secondCall = javaQuestionService.getAll();

        assertEquals(2, firstCall.size());
        assertEquals(2, secondCall.size());
        verify(repository, times(2)).getAll();
    }

    @Test
    void getRandomQuestion_shouldReturnQuestionWhenNotEmpty() {

        Set<Question> mockQuestions = Set.of(
                new Question("Q1", "A1"),
                new Question("Q2", "A2")
        );
        when(repository.getAll()).thenReturn(mockQuestions);

        Question result = javaQuestionService.getRandomQuestion();

        assertNotNull(result);
        assertTrue(mockQuestions.contains(result));
        verify(repository).getAll();
    }

    @Test
    void getRandomQuestion_shouldReturnNullWhenEmpty() {

        when(repository.getAll()).thenReturn(Set.of());

        Question result = javaQuestionService.getRandomQuestion();

        assertNull(result);
        verify(repository).getAll();
    }
}
