package org.skypro.question_generator.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.question_generator.domain.Question;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExaminerServiceImplTest {

    @Mock
    private JavaQuestionService javaQuestionService;

    @Mock
    private MathQuestionService mathQuestionService;

    @InjectMocks
    private ExaminerServiceImpl examinerService;

    @Test
    void getQuestions_shouldReturnJavaQuestions() {
        Set<Question> javaQuestions = Set.of(
                new Question("Java Q1", "A1"),
                new Question("Java Q2", "A2")
        );

        when(javaQuestionService.getAll()).thenReturn(javaQuestions);
        when(javaQuestionService.getRandomQuestion())
                .thenReturn(new Question("Java Q1", "A1"))
                .thenReturn(new Question("Java Q2", "A2"));

        Collection<Question> result = examinerService.getQuestions("java", 2);

        assertEquals(2, result.size());
        verify(javaQuestionService, times(2)).getRandomQuestion();
    }

    @Test
    void getQuestions_shouldReturnMathQuestions() {
        Set<Question> mathQuestions = Set.of(
                new Question("Math Q1", "A1"),
                new Question("Math Q2", "A2")
        );

        when(mathQuestionService.getAll()).thenReturn(mathQuestions);
        when(mathQuestionService.getRandomQuestion())
                .thenReturn(new Question("Math Q1", "A1"))
                .thenReturn(new Question("Math Q2", "A2"));

        Collection<Question> result = examinerService.getQuestions("math", 2);

        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(q -> q.getQuestion().startsWith("Math")));
        verify(mathQuestionService, times(2)).getRandomQuestion();
    }

    @Test
    void getQuestions_shouldThrowWhenSubjectNotFound() {
        assertThrows(ResponseStatusException.class,
                () -> examinerService.getQuestions("physics", 1));
    }

    @Test
    void getQuestions_shouldThrowWhenTooManyRequested() {
        when(javaQuestionService.getAll()).thenReturn(Set.of(new Question("Q", "A")));

        assertThrows(ResponseStatusException.class,
                () -> examinerService.getQuestions("java", 2));
    }

    @Test
    void getQuestions_shouldReturnUniqueQuestions() {
        Question q1 = new Question("Q1", "A1");
        Question q2 = new Question("Q2", "A2");

        when(mathQuestionService.getAll()).thenReturn(Set.of(q1, q2));
        when(mathQuestionService.getRandomQuestion())
                .thenReturn(q1)
                .thenReturn(q2)
                .thenReturn(q1);

        Collection<Question> result = examinerService.getQuestions("math", 2);

        assertEquals(2, result.size());
        assertEquals(2, new HashSet<>(result).size());
    }
}
