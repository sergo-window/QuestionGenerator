package org.skypro.question_generator.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.question_generator.domain.Question;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExaminerServiceImplTest {

    @Mock
    private QuestionService questionService;

    @InjectMocks
    private ExaminerServiceImpl examinerService;

    @Test
    void getQuestions_shouldReturnRequestedAmountOfQuestions() {
        Set<Question> mockQuestions = new HashSet<>();
        mockQuestions.add(new Question("Q1", "A1"));
        mockQuestions.add(new Question("Q2", "A2"));
        mockQuestions.add(new Question("Q3", "A3"));

        when(questionService.getAll()).thenReturn(mockQuestions);
        when(questionService.getRandomQuestion())
                .thenReturn(new Question("Q1", "A1"))
                .thenReturn(new Question("Q2", "A2"));

        var result = examinerService.getQuestions(2);

        assertEquals(2, result.size());
        verify(questionService, times(2)).getRandomQuestion();
    }

    @Test
    void getQuestions_shouldThrowExceptionWhenRequestedTooMany() {
        Set<Question> mockQuestions = new HashSet<>();
        mockQuestions.add(new Question("Q1", "A1"));

        when(questionService.getAll()).thenReturn(mockQuestions);

        assertThrows(ResponseStatusException.class, () -> examinerService.getQuestions(2));
    }

    @Test
    void getQuestions_shouldReturnUniqueQuestions() {
        Question q1 = new Question("Q1", "A1");
        Question q2 = new Question("Q2", "A2");

        when(questionService.getAll()).thenReturn(Set.of(q1, q2));
        when(questionService.getRandomQuestion())
                .thenReturn(q1)
                .thenReturn(q2)
                .thenReturn(q1);

        var result = examinerService.getQuestions(2);

        assertEquals(2, result.size());
        assertEquals(2, new HashSet<>(result).size()); // проверка уникальности
    }
}
